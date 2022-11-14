/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree;

public enum Neo4jEntityViewNodeType implements NodeType {
    NODE,
    NODE_LABELS,
    NODE_PROPERIES,
    NODE_LIST,
    NODE_MAP,
    NODE_VALUE,

    RELATIONSHIP,
    RELATIONSHIP_TYPES,
    RELATIONSHIP_PROPERTIES,
    RELATIONSHIP_LIST,
    RELATIONSHIP_MAP,
    RELATIONSHIP_VALUE,

    PATH,
    OTHER;

    Neo4jEntityViewNodeType() {
    }
}
