/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.database.neo4j_3_5;

import com.albertoventurini.graphdbplugin.test.database.neo4j.common.Neo4jServer;
import org.neo4j.harness.ServerControls;
import org.neo4j.harness.internal.InProcessServerBuilder;

@SuppressWarnings("Duplicates")
public class Neo4j35Server implements Neo4jServer {

    private ServerControls serverControls;

    @Override
    public void start() {
        if (serverControls == null) {
            serverControls = new InProcessServerBuilder()
                    .withFunction(Neo4jTestUserFunction.class)
                    .newServer();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    serverControls.close();
                } catch (Exception ignored) {
                }
            }));
        }
    }

    @Override
    public String getBoltHost() {
        return serverControls.boltURI().getHost();
    }

    @Override
    public String getBoltPort() {
        return String.valueOf(serverControls.boltURI().getPort());
    }
}
