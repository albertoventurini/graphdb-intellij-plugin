/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.database.api;

import com.albertoventurini.graphdbplugin.database.api.data.GraphMetadata;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResult;

import java.util.Map;

public interface GraphDatabaseApi {

    GraphQueryResult execute(String query);

    GraphQueryResult execute(String query, Map<String, Object> statementParameters);

    GraphMetadata metadata();
}
