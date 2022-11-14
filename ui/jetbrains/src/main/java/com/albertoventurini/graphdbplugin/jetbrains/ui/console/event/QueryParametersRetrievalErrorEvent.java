/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console.event;

import com.intellij.openapi.editor.Editor;
import com.intellij.util.messages.Topic;

public interface QueryParametersRetrievalErrorEvent {

    Topic<QueryParametersRetrievalErrorEvent> QUERY_PARAMETERS_RETRIEVAL_ERROR_EVENT_TOPIC =
            Topic.create("GraphDatabaseConsole.QueryParametersRetrievalErrorEventTopic", QueryParametersRetrievalErrorEvent.class);

    String PARAMS_ERROR_COMMON_MSG = "Failed to retrieve query parameters";
    void handleError(Exception exception, Editor editor);

}
