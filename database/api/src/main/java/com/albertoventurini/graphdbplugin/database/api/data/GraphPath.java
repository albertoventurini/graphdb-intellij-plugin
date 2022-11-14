/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.database.api.data;

import java.util.List;

public interface GraphPath extends NoIdGraphEntity {

    /**
     * Return nodes and relationships.
     */
    List<Object> getComponents();

    default boolean isTypesSingle() {
        return true;
    }

    default String getRepresentation() {
        return "Path";
    }
}
