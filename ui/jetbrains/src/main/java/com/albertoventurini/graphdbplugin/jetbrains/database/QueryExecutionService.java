/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.database;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import com.albertoventurini.graphdbplugin.database.api.GraphDatabaseApi;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResult;
import com.albertoventurini.graphdbplugin.jetbrains.actions.execute.ExecuteQueryPayload;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.event.QueryExecutionProcessEvent;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.event.QueryPlanEvent;
import com.albertoventurini.graphdbplugin.jetbrains.util.Notifier;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Future;

// TODO: consider making this a plugin service.
public class QueryExecutionService {

    private final DatabaseManagerService databaseManager;
    private final MessageBus messageBus;
    private Future<?> runningQuery;

    public QueryExecutionService(
            @NotNull final Project project,
            final MessageBus messageBus) {
        this.messageBus = messageBus;
        this.databaseManager = project.getService(DatabaseManagerService.class);
    }

    public void executeQuery(DataSourceApi dataSource, ExecuteQueryPayload payload) {
        checkForRunningQuery();

        try {
            executeInBackground(dataSource, payload);
        } catch (Exception e) {
            Notifier.error("Query execution", "Error during execution: " + e.toString());
        }
    }

    private synchronized void checkForRunningQuery() {
        if (runningQuery == null) {
            return;
        }

        if (runningQuery.isDone()) {
            runningQuery = null;
        } else {
            runningQuery.cancel(true);
        }
    }

    private synchronized void executeInBackground(DataSourceApi dataSource, ExecuteQueryPayload payload) {
        QueryExecutionProcessEvent event = messageBus.syncPublisher(QueryExecutionProcessEvent.QUERY_EXECUTION_PROCESS_TOPIC);
        event.executionStarted(dataSource, payload);

        if (payload.getQueries().size() == 1) {
            runningQuery = ApplicationManager.getApplication()
                .executeOnPooledThread(executeQuery(dataSource, payload, event));
        } else {
            runningQuery = ApplicationManager.getApplication()
                .executeOnPooledThread(executeBatch(dataSource, payload, event));
        }
    }

    @NotNull
    private Runnable executeQuery(DataSourceApi dataSource, ExecuteQueryPayload payload, QueryExecutionProcessEvent event) {
        return () -> {
            if (payload.getQueries().size() != 1) {
                return;
            }

            try {
                GraphDatabaseApi database = databaseManager.getDatabaseFor(dataSource);

                String query = payload.getQueries().get(0);
                GraphQueryResult result = database.execute(query, payload.getParameters());

                ApplicationManager.getApplication().invokeLater(() -> {
                    event.resultReceived(payload, result);
                    event.postResultReceived(payload);
                    event.executionCompleted(payload);

                    if (result.hasPlan()) {
                        QueryPlanEvent queryPlanEvent = messageBus.syncPublisher(QueryPlanEvent.QUERY_PLAN_EVENT);
                        queryPlanEvent.queryPlanReceived(query, result);
                    }
                });
            } catch (Exception e) {
                ApplicationManager.getApplication().invokeLater(() -> {
                    event.handleError(payload, e);
                    event.executionCompleted(payload);
                });
            }
        };
    }

    @NotNull
    private Runnable executeBatch(DataSourceApi dataSource, ExecuteQueryPayload payload, QueryExecutionProcessEvent event) {
        return () -> {
            try {
                GraphDatabaseApi database = databaseManager.getDatabaseFor(dataSource);
                for (String query : payload.getQueries()) {
                     database.execute(query, payload.getParameters());
                }
            } catch (Exception e) {
                ApplicationManager.getApplication().invokeLater(() -> {
                    event.handleError(payload, e);
                    event.executionCompleted(payload);
                });
            }
        };
    }
}
