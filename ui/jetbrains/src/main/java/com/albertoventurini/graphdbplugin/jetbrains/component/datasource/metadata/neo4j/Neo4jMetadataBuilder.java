package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j;

import com.albertoventurini.graphdbplugin.database.api.GraphDatabaseApi;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResult;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResultColumn;
import com.albertoventurini.graphdbplugin.database.neo4j.bolt.query.Neo4jBoltQueryResultRow;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourceMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.MetadataBuilder;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.database.DatabaseManagerService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Neo4jMetadataBuilder implements MetadataBuilder {

    private static final Logger LOG = Logger.getInstance(Neo4jMetadataBuilder.class);

    @Override
    public DataSourceMetadata buildMetadata(DataSourceApi dataSource) {
        final var databaseManager = ApplicationManager.getApplication().getService(DatabaseManagerService.class);
        GraphDatabaseApi db = databaseManager.getDatabaseFor(dataSource);
        Neo4jBoltCypherDataSourceMetadata metadata = new Neo4jBoltCypherDataSourceMetadata();

        try {
            GraphQueryResult indexesResult = db.execute("SHOW INDEXES");
            GraphQueryResult storedProceduresResult = db.execute("SHOW PROCEDURES YIELD name, signature, description");

            metadata.addIndexes(indexesResult);
            metadata.addProcedures(storedProceduresResult);

            metadata.addConstraints(getConstraints(db));
        } catch (Exception e) {
            LOG.warn("Unable to load indexes, constraints and procedures from the current database. Please upgrade to Neo4j 4 or 5 to fix this.");
        }

        GraphQueryResult labelsQueryResult = db.execute("CALL db.labels()");
        GraphQueryResult relationshipQueryResult = db.execute("CALL db.relationshipTypes()");

        final var propertyKeys = getPropertyKeys(db);
        metadata.addPropertyKeys(propertyKeys);

        List<String> listOfLabels = extractLabels(labelsQueryResult);
        if (!listOfLabels.isEmpty()) {
            GraphQueryResult labelCount = db.execute(queryLabelCount(listOfLabels));
            metadata.addLabels(labelCount, listOfLabels);
        }

        List<String> listOfRelationshipTypes = extractRelationshipTypes(relationshipQueryResult);
        if (!listOfRelationshipTypes.isEmpty()) {
            GraphQueryResult relationshipTypeCountResult = db.execute(queryRelationshipTypeCount(listOfRelationshipTypes));
            metadata.addRelationshipTypes(relationshipTypeCountResult, listOfRelationshipTypes);
        }

        final GraphQueryResult functionsResult = db.execute("SHOW FUNCTIONS YIELD name, signature, description");
        metadata.addFunctions(functionsResult);

        return metadata;
    }

    private List<Neo4jConstraintMetadata> getConstraints(final GraphDatabaseApi db) {
        return db.execute("SHOW CONSTRAINTS").getRows().stream().map(row -> {
            final Neo4jBoltQueryResultRow neo4jRow = (Neo4jBoltQueryResultRow) row;
            final String name = neo4jRow.getValue("name").asString();
            return new Neo4jConstraintMetadata(name);
        }).collect(toList());
    }

    private List<String> getPropertyKeys(final GraphDatabaseApi db) {
        return db.execute("CALL db.propertyKeys()").getRows().stream().map(row -> {
            final Neo4jBoltQueryResultRow neo4jRow = (Neo4jBoltQueryResultRow) row;
            return neo4jRow.getValue("propertyKey").asString();
        }).collect(toList());
    }

    private List<String> extractRelationshipTypes(GraphQueryResult relationshipQueryResult) {
        GraphQueryResultColumn column = relationshipQueryResult.getColumns().get(0);
        return relationshipQueryResult.getRows()
                .stream()
                .map(row -> (String) row.getValue(column))
                .collect(toList());
    }

    private List<String> extractLabels(GraphQueryResult labelsQueryResult) {
        GraphQueryResultColumn column = labelsQueryResult.getColumns().get(0);
        return labelsQueryResult.getRows()
                .stream()
                .map(row -> (String) row.getValue(column))
                .collect(toList());
    }

    private String queryRelationshipTypeCount(List<String> relationshipTypes) {
        return relationshipTypes
                .stream()
                .map(relationshipType -> "MATCH ()-[r:`" + relationshipType + "`]->() RETURN count(r)")
                .collect(Collectors.joining(" UNION ALL "));
    }

    private String queryLabelCount(List<String> labels) {
        return labels
                .stream()
                .map(label -> "MATCH (n:`" + label + "`) RETURN count(n)")
                .collect(Collectors.joining(" UNION ALL "));
    }

}
