/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console.graph;

import com.albertoventurini.graphdbplugin.jetbrains.actions.execute.ExecuteQueryEvent;
import com.albertoventurini.graphdbplugin.jetbrains.actions.execute.ExecuteQueryPayload;
import com.albertoventurini.graphdbplugin.jetbrains.actions.ui.console.CleanCanvasEvent;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.database.QueryExecutionService;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.event.QueryExecutionProcessEvent;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResult;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.GraphConsoleView;
import com.albertoventurini.graphdbplugin.visualization.VisualizationApi;
import com.albertoventurini.graphdbplugin.visualization.events.EventType;
import org.jetbrains.annotations.NotNull;

public class GraphPanelInteractions {

    private final GraphConsoleView graphConsoleView;
    private final MessageBus messageBus;
    private final QueryExecutionService queryExecutionService;
    private final VisualizationApi visualization;

    public GraphPanelInteractions(
            @NotNull final Project project,
            final GraphConsoleView graphConsoleView,
            final MessageBus messageBus,
            final VisualizationApi visualization) {
        this.graphConsoleView = graphConsoleView;
        this.messageBus = messageBus;
        this.visualization = visualization;
        this.queryExecutionService = new QueryExecutionService(project, messageBus);

        registerMessageBusSubscribers();
        registerVisualisationEvents();
    }

    private void registerMessageBusSubscribers() {
        messageBus.connect()
                .subscribe(ExecuteQueryEvent.EXECUTE_QUERY_TOPIC, queryExecutionService::executeQuery);
        messageBus.connect()
                .subscribe(CleanCanvasEvent.CLEAN_CANVAS_TOPIC, () -> {
                    visualization.stop();
                    visualization.clear();
                    visualization.paint();
                });
        messageBus.connect()
                .subscribe(QueryExecutionProcessEvent.QUERY_EXECUTION_PROCESS_TOPIC, new QueryExecutionProcessEvent() {
                    @Override
                    public void executionStarted(DataSourceApi dataSource, ExecuteQueryPayload payload) {
                        visualization.stop();
                        visualization.clear();
                    }

                    @Override
                    public void resultReceived(ExecuteQueryPayload payload, GraphQueryResult result) {
                        result.getNodes().forEach(visualization::addNode);
                        result.getRelationships().forEach(visualization::addRelation);
                    }

                    @Override
                    public void postResultReceived(ExecuteQueryPayload payload) {
                        visualization.paint();
                    }

                    @Override
                    public void handleError(ExecuteQueryPayload payload, Exception exception) {
                        String errorMessage = exception.getMessage() == null ? "Error occurred" : "Error occurred: " + exception.getMessage();
                        payload.getEditor().ifPresent(editor -> HintManager.getInstance().showErrorHint(editor, errorMessage));

                        visualization.stop();
                        visualization.clear();
                    }

                    @Override
                    public void executionCompleted(ExecuteQueryPayload payload) {
                    }
                });
    }

    private void registerVisualisationEvents() {
        visualization.addNodeListener(EventType.CLICK, graphConsoleView.getGraphPanel()::showNodeData);
        visualization.addEdgeListener(EventType.CLICK, graphConsoleView.getGraphPanel()::showRelationshipData);
        visualization.addNodeListener(EventType.HOVER_START, graphConsoleView.getGraphPanel()::showTooltip);
        visualization.addNodeListener(EventType.HOVER_END, graphConsoleView.getGraphPanel()::hideTooltip);
        visualization.addEdgeListener(EventType.HOVER_START, graphConsoleView.getGraphPanel()::showTooltip);
        visualization.addEdgeListener(EventType.HOVER_END, graphConsoleView.getGraphPanel()::hideTooltip);
    }
}
