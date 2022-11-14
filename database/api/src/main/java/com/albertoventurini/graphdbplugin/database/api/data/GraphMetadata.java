/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.database.api.data;

import java.util.Map;

public interface GraphMetadata {
    Map<String, Number> labels();

    Map<String, Number> relationships();

    Iterable<String> vertexProperties();

    Iterable<String> edgeProperties();
}
