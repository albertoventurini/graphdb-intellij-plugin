/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console.event;

import com.intellij.util.messages.Topic;

public interface CopyQueryOutputEvent {

    Topic<CopyQueryOutputEvent> COPY_QUERY_OUTPUT_TOPIC = Topic.create("GraphDatabaseConsole.CopyQueryOutputTopic", CopyQueryOutputEvent.class);

    void copyQueryOutputToClipboard();
}
