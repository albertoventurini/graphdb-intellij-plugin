/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.util.server;

import com.albertoventurini.graphdbplugin.test.database.neo4j.common.Neo4jServer;

public class Neo4j35ServerLoader extends Neo4jServerLoader {

    private static Neo4j35ServerLoader neo4jServer;

    public static synchronized Neo4jServer getInstance() {
        if (neo4jServer == null) {
            neo4jServer = new Neo4j35ServerLoader();
            neo4jServer.start();
        }
        return neo4jServer;
    }

    @Override
    protected String getLibraryPath() {
        return System.getProperty("neo4j-package-3.5");
    }

    @Override
    protected String getNeo4jServerClass() {
        return "com.neueda.jetbrains.plugin.graphdb.test.database.neo4j_3_5.Neo4j35Server";
    }
}
