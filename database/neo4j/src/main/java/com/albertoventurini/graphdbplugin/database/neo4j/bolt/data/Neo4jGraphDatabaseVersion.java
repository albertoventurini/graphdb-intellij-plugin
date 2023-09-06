/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.database.neo4j.bolt.data;

import com.albertoventurini.graphdbplugin.database.api.data.GraphDatabaseVersion;

public record Neo4jGraphDatabaseVersion(int major, int minor, int patch) implements GraphDatabaseVersion {

    @Override
    public String toString() {
        return "Neo4j/" + major + "." + minor + "." + patch;
    }

    @Override
    public String idFunction() {
        if (major >= 5) {
            return "elementId";
        }
        return "id";
    }

    @Override
    public Object idToParameter(String id) {
        if (major >= 5) {
            return id;
        }
        return Long.parseLong(id);
    }
}
