/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.database.neo4j.bolt.data;

import com.albertoventurini.graphdbplugin.database.api.data.GraphPropertyContainer;

import java.util.Map;

public class Neo4jBoltPropertyContainer implements GraphPropertyContainer {

    private final Map<String, Object> properties;

    public Neo4jBoltPropertyContainer(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }
}
