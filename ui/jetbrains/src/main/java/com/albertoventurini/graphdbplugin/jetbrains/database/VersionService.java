/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.database;

import com.albertoventurini.graphdbplugin.database.api.data.GraphDatabaseVersion;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.services.ExecutorService;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.event.VersionFetchingProcessEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public final class VersionService {
    private final HashMap<String, CompletableFuture<GraphDatabaseVersion>> versionRegistry;
    private final MessageBus messageBus;
    private final DatabaseManagerService databaseManager;
    private final ExecutorService executorService;

    VersionService(Project project) {
        versionRegistry = new HashMap<>();
        messageBus = project.getMessageBus();
        databaseManager = ApplicationManager.getApplication().getService(DatabaseManagerService.class);
        executorService = ApplicationManager.getApplication().getService(ExecutorService.class);
    }

    public synchronized CompletableFuture<GraphDatabaseVersion> getVersion(DataSourceApi api) {
        final var key = api.getName();
        final var version = versionRegistry.get(key);
        if (version != null && !version.isCompletedExceptionally()) {
            return version;
        }
        final var future = fetchVersion(api);
        versionRegistry.put(key, future);
        return future;
    }

    public synchronized void updatedVersion(DataSourceApi api, GraphDatabaseVersion version) {
        final var key = api.getName();
        final var future = new CompletableFuture<GraphDatabaseVersion>();
        future.complete(version);
        versionRegistry.put(key, future);
    }

    private CompletableFuture<GraphDatabaseVersion> fetchVersion(DataSourceApi api) {
        VersionFetchingProcessEvent event = messageBus.syncPublisher(VersionFetchingProcessEvent.VERSION_FETCHING_PROCESS_TOPIC);
        event.processStarted(api);
        final var future = new CompletableFuture<GraphDatabaseVersion>();
        final var db = databaseManager.getDatabaseFor(api);
        executorService.runInBackground(
                db::getVersion,
                version -> {
                    event.versionReceived(version);
                    future.complete(version);
                },
                ex -> {
                    event.handleError(ex);
                    future.completeExceptionally(ex);
                }
        );
        return future;
    }
}
