/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.database.neo4j.bolt;

import java.util.Map;

public class Neo4jBoltConfiguration {

    public static final String HOST = "host";
    public static final String PORT = "port";
    public static final String USER = "user";
    public static final String PASSWORD = "password";

    private final Map<String, String> configuration;

    public Neo4jBoltConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public String getHost() {
        return configuration.get(HOST);
    }

    public Integer getPort() {
        return Integer.valueOf(configuration.getOrDefault(PORT, "7687"));
    }

    public String getUser() {
        return configuration.get(USER);
    }

    public String getPassword() {
        return configuration.get(PASSWORD);
    }
}
