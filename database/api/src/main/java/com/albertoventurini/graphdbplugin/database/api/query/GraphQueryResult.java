/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.database.api.query;

import com.albertoventurini.graphdbplugin.database.api.data.GraphNode;
import com.albertoventurini.graphdbplugin.database.api.data.GraphRelationship;

import java.util.List;
import java.util.Optional;

public interface GraphQueryResult {

    long getExecutionTimeMs();

    String getResultSummary();

    List<GraphQueryResultColumn> getColumns();

    List<GraphQueryResultRow> getRows();

    List<GraphNode> getNodes();

    List<GraphRelationship> getRelationships();

    List<GraphQueryNotification> getNotifications();

    boolean hasPlan();

    boolean isProfilePlan();

    Optional<GraphQueryPlan> getPlan();
}
