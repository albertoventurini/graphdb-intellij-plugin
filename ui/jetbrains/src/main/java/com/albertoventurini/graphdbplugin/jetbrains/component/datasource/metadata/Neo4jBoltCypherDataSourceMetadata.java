/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata;

import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResult;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResultColumn;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResultRow;
import com.albertoventurini.graphdbplugin.database.neo4j.bolt.data.Neo4jBoltQueryResultRow;

import java.util.*;

public class Neo4jBoltCypherDataSourceMetadata implements DataSourceMetadata {

    public static final String INDEXES = "indexes";
    public static final String CONSTRAINTS = "constraints";
    public static final String PROPERTY_KEYS = "propertyKeys";

    private Map<String, List<Map<String, String>>> dataReceiver = new HashMap<>();

    private final List<Neo4jFunctionMetadata> functions = new ArrayList<>();
    private final List<Neo4jProcedureMetadata> procedures = new ArrayList<>();

    private List<Neo4jLabelMetadata> labels = new ArrayList<>();
    private List<Neo4jRelationshipTypeMetadata> relationshipTypes = new ArrayList<>();

    @Override
    public List<Map<String, String>> getMetadata(String metadataKey) {
        return dataReceiver.getOrDefault(metadataKey, new ArrayList<>());
    }

    @Override
    public List<Neo4jFunctionMetadata> getFunctions() {
        return Collections.unmodifiableList(functions);
    }

    @Override
    public List<Neo4jProcedureMetadata> getProcedures() {
        return Collections.unmodifiableList(procedures);
    }

    @Override
    public boolean isMetadataExists(final String metadataKey) {
        return dataReceiver.containsKey(metadataKey);
    }

    public void addPropertyKeys(GraphQueryResult propertyKeysResult) {
        addDataSourceMetadata(PROPERTY_KEYS, propertyKeysResult);
    }

    public void addProcedure(final Neo4jProcedureMetadata procedure) {
        procedures.add(procedure);
    }

    public void addProcedures(final GraphQueryResult proceduresResult) {
        proceduresResult.getRows().forEach(row -> {
            final Neo4jBoltQueryResultRow neo4jRow = (Neo4jBoltQueryResultRow) row;
            final String name = neo4jRow.getValue("name", String.class);
            final String signature = neo4jRow.getValue("signature", String.class);
            final String description = neo4jRow.getValue("description", String.class);
            procedures.add(new Neo4jProcedureMetadata(name, signature, description));
        });
    }

    public void addFunctions(final GraphQueryResult functionsResult) {
        functionsResult.getRows().forEach(row -> {
            final Neo4jBoltQueryResultRow neo4jRow = (Neo4jBoltQueryResultRow) row;
            final String name = neo4jRow.getValue("name", String.class);
            final String signature = neo4jRow.getValue("signature", String.class);
            final String description = neo4jRow.getValue("description", String.class);
            functions.add(new Neo4jFunctionMetadata(name, signature, description));
        });
    }

    private void addDataSourceMetadata(String key, GraphQueryResult graphQueryResult) {
        List<Map<String, String>> dataSourceMetadata = new ArrayList<>();

        List<GraphQueryResultColumn> columns = graphQueryResult.getColumns();
        for (GraphQueryResultRow row : graphQueryResult.getRows()) {
            Map<String, String> data = new HashMap<>();

            for (GraphQueryResultColumn column : columns) {
                Object value = row.getValue(column);
                if (value != null) {
                    data.put(column.getName(), value.toString());
                }
            }

            dataSourceMetadata.add(data);
        }

        dataReceiver.put(key, dataSourceMetadata);
    }

    public void addDataSourceMetadata(String key, List<Map<String, String>> data) {
        dataReceiver.put(key, data);
    }

    public void addLabels(GraphQueryResult labelCountResult, List<String> labelNames) {
        GraphQueryResultColumn column = labelCountResult.getColumns().get(0);
        for (int i = 0; i < labelCountResult.getRows().size(); i++) {
            GraphQueryResultRow row = labelCountResult.getRows().get(i);
            labels.add(new Neo4jLabelMetadata(labelNames.get(i), (Long) row.getValue(column)));
        }
    }

    public void addLabel(Neo4jLabelMetadata labelMetadata) {
        labels.add(labelMetadata);
    }

    public List<Neo4jLabelMetadata> getLabels() {
        return labels;
    }

    public void addRelationshipTypes(GraphQueryResult relationshipTypeCountResult, List<String> relationshipTypeNames) {
        GraphQueryResultColumn column = relationshipTypeCountResult.getColumns().get(0);
        for (int i = 0; i < relationshipTypeCountResult.getRows().size(); i++) {
            GraphQueryResultRow row = relationshipTypeCountResult.getRows().get(i);
            relationshipTypes.add(new Neo4jRelationshipTypeMetadata(relationshipTypeNames.get(i), (Long) row.getValue(column)));
        }
    }

    public void addRelationshipType(Neo4jRelationshipTypeMetadata relationshipTypeMetadata) {
        relationshipTypes.add(relationshipTypeMetadata);
    }

    public void addPropertyKey(String key) {
        List<Map<String, String>> propertyKeys = getMetadata(PROPERTY_KEYS);
        propertyKeys.add(Collections.singletonMap("propertyKey", key));
        dataReceiver.put(PROPERTY_KEYS, propertyKeys);
    }

    public List<Neo4jRelationshipTypeMetadata> getRelationshipTypes() {
        return relationshipTypes;
    }

    public void addIndexes(GraphQueryResult indexesResult) {
        addDataSourceMetadata(INDEXES, indexesResult);
    }

    public void addConstraints(GraphQueryResult constraintsResult) {
        addDataSourceMetadata(CONSTRAINTS, constraintsResult);
    }
}
