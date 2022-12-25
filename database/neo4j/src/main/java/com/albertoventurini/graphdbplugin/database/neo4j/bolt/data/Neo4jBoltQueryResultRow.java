/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.database.neo4j.bolt.data;

import com.albertoventurini.graphdbplugin.database.api.data.GraphNode;
import com.albertoventurini.graphdbplugin.database.api.data.GraphRelationship;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResultColumn;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResultRow;
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

    private final Map<String, Object> row;
    private final List<GraphNode> nodes;
    private final List<GraphRelationship> relationships;

    public Neo4jBoltQueryResultRow(Map<String, Object> sourceRow) {
        this.row = new HashMap<>();
        this.nodes = new ArrayList<>();
        this.relationships = new ArrayList<>();

        transform(sourceRow);
    }

    public Object getValue(final String columnName) {
        return row.get(columnName);
    }

    @Override
    public Object getValue(GraphQueryResultColumn column) {
        return row.get(column.getName());
    }

    @Override
    public List<GraphNode> getNodes() {
        return nodes;
    }

    @Override
    public List<GraphRelationship> getRelationships() {
        return relationships;
    }

    private void transform(Map<String, Object> sourceRow) {
        sourceRow.forEach((key, value) -> row.put(key, convert(value)));
    }

    @SuppressWarnings("unchecked")
    private Object convert(Object o) {
        if (o instanceof Map<?,?> originalMap) {
            Map<Object, Object> map = new HashMap<>();

            originalMap.forEach((key, value) -> map.put(convert(key), convert(value)));
            return map;
        }
        if (o instanceof List<?> originalList) {
            return originalList.stream()
                    .map(this::convert)
                    .collect(Collectors.toList());
        }
        if (o instanceof Node node) {
            Neo4jBoltNode boltNode = new Neo4jBoltNode(node);
            nodes.add(boltNode);
            return boltNode;
        }
        if (o instanceof Relationship rel) {
            Neo4jBoltRelationship boltRelationship = new Neo4jBoltRelationship(rel);
            relationships.add(boltRelationship);
            return boltRelationship;
        }
        if (o instanceof Path path) {
            Neo4jBoltPath boltPath = new Neo4jBoltPath();

            if (path.length() == 0) {
                return boltPath;
            }

            List<Node> pathNodes = Iterables.asList(path.nodes());
            List<Relationship> relationships = Iterables.asList(path.relationships());

            for (int i = 0; i < path.length(); i++) {
                boltPath.add(convert(pathNodes.get(i)));
                boltPath.add(convert(relationships.get(i)));

                if (i == path.length() - 1) {
                    boltPath.add(convert(pathNodes.get(i + 1)));
                }
            }
            return boltPath;
        }

        // Just use object itself
        return o;
    }
}
