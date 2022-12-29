/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.database.api.query;

import com.albertoventurini.graphdbplugin.database.api.data.GraphNode;
import com.albertoventurini.graphdbplugin.database.api.data.GraphRelationship;

import java.util.List;
import java.util.Optional;

public interface GraphQueryResultRow {

    Object getValue(final String columnName);

    Object getValue(GraphQueryResultColumn column);

    List<GraphNode> getNodes();

    List<GraphRelationship> getRelationships();

    default <T> T getValue(final String columnName, final Class<T> clazz) {
        final Object value = getValue(columnName);
        if (clazz.isInstance(value)) {
            return clazz.cast(value);
        }
        throw new ClassCastException("Unable to cast value to " + clazz.getName() + " at column " + columnName);
    }

    default <T> Optional<T> getOptionalValue(final String columnName, final Class<T> clazz) {
        return Optional.ofNullable(getValue(columnName))
                .map(v -> {
                    if (clazz.isInstance(v)) {
                        return clazz.cast(v);
                    }
                    throw new ClassCastException("Unable to cast value to " + clazz.getName() + " at column " + columnName);
                });
    }
}
