/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree;

public enum Neo4jTreeNodeType implements NodeType {
    ROOT,
    DATASOURCE,
    INDEXES,
    INDEX,
    CONSTRAINTS,
    CONSTRAINT,
    LABELS,
    LABEL,
    RELATIONSHIPS,
    RELATIONSHIP,
    PROPERTY_KEYS,
    PROPERTY_KEY,
    STORED_PROCEDURES,
    STORED_PROCEDURE,
    FUNCTIONS,
    FUNCTION;

    Neo4jTreeNodeType() {
    }

}
