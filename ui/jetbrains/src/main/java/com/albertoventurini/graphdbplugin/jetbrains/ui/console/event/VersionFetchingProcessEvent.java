/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console.event;

import com.albertoventurini.graphdbplugin.database.api.data.GraphDatabaseVersion;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.intellij.util.messages.Topic;

public interface VersionFetchingProcessEvent {

    Topic<VersionFetchingProcessEvent> VERSION_FETCHING_PROCESS_TOPIC =
            Topic.create("GraphDatabaseConsole.VersionFetchingProcessTopic", VersionFetchingProcessEvent.class);

    void processStarted(DataSourceApi dataSource);

    void versionReceived(GraphDatabaseVersion version);


    void handleError(Exception exception);
}
