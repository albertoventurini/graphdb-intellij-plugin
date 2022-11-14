/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console.event;

import com.intellij.util.messages.Topic;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResult;

public interface QueryPlanEvent {

    Topic<QueryPlanEvent> QUERY_PLAN_EVENT = Topic.create("GraphDatabaseConsole.QueryPlanEvent", QueryPlanEvent.class);

    void queryPlanReceived(String query, GraphQueryResult result);
}
