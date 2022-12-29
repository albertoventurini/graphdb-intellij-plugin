/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.database.neo4j.bolt.query;

import com.albertoventurini.graphdbplugin.database.api.data.GraphNode;
import com.albertoventurini.graphdbplugin.database.api.data.GraphRelationship;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResultColumn;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResultRow;
import com.albertoventurini.graphdbplugin.database.neo4j.bolt.data.Neo4jBoltNode;
import com.albertoventurini.graphdbplugin.database.neo4j.bolt.data.Neo4jBoltPath;
import com.albertoventurini.graphdbplugin.database.neo4j.bolt.data.Neo4jBoltRelationship;
import org.neo4j.driver.Record;
import org.neo4j.driver.internal.util.Iterables;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Neo4jBoltQueryResultRow implements GraphQueryResultRow {

    private final Record record;
    private final List<GraphNode> nodes;
    private final List<GraphRelationship> relationships;

    public Neo4jBoltQueryResultRow(final Record record) {
        this.record = record;
        this.nodes = new ArrayList<>();
        this.relationships = new ArrayList<>();

        record.asMap().forEach((k, v) -> {
            collectNodesAndRelationships(k);
            collectNodesAndRelationships(v);
        });
    }

    public Object getValue(final String columnName) {
        return record.get(columnName).asObject();
    }

    @Override
    public Object getValue(GraphQueryResultColumn column) {
        return record.get(column.getName()).asObject();
    }

    @Override
    public List<GraphNode> getNodes() {
        return nodes;
    }

    @Override
    public List<GraphRelationship> getRelationships() {
        return relationships;
    }

    @SuppressWarnings("unchecked")
    private void collectNodesAndRelationships(Object o) {
        if (o instanceof Map<?,?> map) {
            map.forEach((key, value) -> {
                collectNodesAndRelationships(key);
                collectNodesAndRelationships(value);
            });
        }
        if (o instanceof List<?> list) {
            list.forEach(this::collectNodesAndRelationships);
        }
        if (o instanceof Node node) {
            nodes.add(new Neo4jBoltNode(node));
        }
        if (o instanceof Relationship rel) {
            relationships.add(new Neo4jBoltRelationship(rel));
        }
        if (o instanceof Path path) {
            path.nodes().forEach(n -> nodes.add(new Neo4jBoltNode(n)));
            path.relationships().forEach(r -> relationships.add(new Neo4jBoltRelationship(r)));
        }
    }
}
