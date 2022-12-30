/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j;

import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResult;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResultColumn;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResultRow;
import com.albertoventurini.graphdbplugin.database.neo4j.bolt.query.Neo4jBoltQueryResultRow;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourceMetadata;

import java.util.*;

public class Neo4jBoltCypherDataSourceMetadata implements DataSourceMetadata {

    private Map<String, List<Map<String, String>>> dataReceiver = new HashMap<>();

    private final List<Neo4jFunctionMetadata> functions = new ArrayList<>();
    private final List<Neo4jProcedureMetadata> procedures = new ArrayList<>();
    private final List<Neo4jConstraintMetadata> constraints = new ArrayList<>();

    private List<Neo4jLabelMetadata> labels = new ArrayList<>();
    private List<Neo4jRelationshipTypeMetadata> relationshipTypes = new ArrayList<>();
    private List<Neo4jIndexMetadata> indexes = new ArrayList<>();
    private List<String> propertyKeys = new ArrayList<>();

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

    public List<Neo4jIndexMetadata> getIndexes() {
        return Collections.unmodifiableList(indexes);
    }

    public List<Neo4jConstraintMetadata> getConstraints() {
        return Collections.unmodifiableList(constraints);
    }

    public List<String> getPropertyKeys() {
        return Collections.unmodifiableList(propertyKeys);
    }

    @Override
    public boolean isMetadataExists(final String metadataKey) {
        return dataReceiver.containsKey(metadataKey);
    }

    public void addPropertyKeys(final List<String> propertyKeys) {
        this.propertyKeys.addAll(propertyKeys);
    }

    public void addProcedure(final Neo4jProcedureMetadata procedure) {
        procedures.add(procedure);
    }

    public void addIndex(final Neo4jIndexMetadata indexMetadata) {
        indexes.add(indexMetadata);
    }

    public void addProcedures(final List<Neo4jProcedureMetadata> procedures) {
        this.procedures.addAll(procedures);
    }

    public void addFunctions(final List<Neo4jFunctionMetadata> functions) {
        this.functions.addAll(functions);
    }

    public void addIndexes(final List<Neo4jIndexMetadata> indexes) {
        this.indexes.addAll(indexes);
    }

    public void addLabels(final List<Neo4jLabelMetadata> labels) {
        this.labels.addAll(labels);
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

    public List<Neo4jRelationshipTypeMetadata> getRelationshipTypes() {
        return relationshipTypes;
    }

    public void addConstraints(final Collection<Neo4jConstraintMetadata> constraintMetadata) {
        constraints.addAll(constraintMetadata);
    }
}
