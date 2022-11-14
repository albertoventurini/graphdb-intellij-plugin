/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.database.neo4j.bolt.data;

import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResultColumn;

public class Neo4jBoltQueryResultColumn implements GraphQueryResultColumn {

    private final String name;

    public Neo4jBoltQueryResultColumn(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
