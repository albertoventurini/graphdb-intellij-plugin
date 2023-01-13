package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j;

import com.albertoventurini.graphdbplugin.database.api.GraphDatabaseApi;
import com.albertoventurini.graphdbplugin.database.neo4j.bolt.query.Neo4jBoltQueryResultRow;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourceMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.MetadataBuilder;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.database.DatabaseManagerService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Neo4jMetadataBuilder implements MetadataBuilder {

    private static final String FUNCTIONS_QUERY = """
            SHOW FUNCTIONS YIELD name, signature, description
            """;

    private static final String PROCEDURES_QUERY = """
            SHOW PROCEDURES YIELD name, signature, description
            """;

    private static final String INDEXES_QUERY = "SHOW INDEXES";

    private static final String CONSTRAINTS_QUERY = "SHOW CONSTRAINTS";

    private static final String PROPERTY_KEYS_QUERY = "CALL db.propertyKeys()";

    private static final String LABELS_QUERY = """
            MATCH (n)
            WITH DISTINCT LABELS(n) AS labels, COUNT(n) AS cnt
            UNWIND labels AS labelName
            RETURN labelName, SUM(cnt) AS labelCount
            """;

    private static final String RELATIONSHIP_TYPES_QUERY = """
            MATCH ()-[r]->()
            WITH DISTINCT TYPE(r) AS relationshipTypeName, COUNT(r) AS relationshipTypeCount
            RETURN relationshipTypeName, relationshipTypeCount
            """;

    private static final Logger LOG = Logger.getInstance(Neo4jMetadataBuilder.class);

    @Override
    public DataSourceMetadata buildMetadata(DataSourceApi dataSource) {
        final var databaseManager = ApplicationManager.getApplication().getService(DatabaseManagerService.class);
        final GraphDatabaseApi db = databaseManager.getDatabaseFor(dataSource);

        final List<Neo4jIndexMetadata> indexes = new ArrayList<>();
        final List<Neo4jProcedureMetadata> procedures = new ArrayList<>();
        final List<Neo4jConstraintMetadata> constraints = new ArrayList<>();
        final List<Neo4jFunctionMetadata> functions = new ArrayList<>();

        try {
            indexes.addAll(getIndexes(db));
            procedures.addAll(getProcedures(db));
            constraints.addAll(getConstraints(db));
            functions.addAll(getFunctions(db));
        } catch (Exception e) {
            LOG.warn("Unable to load indexes, constraints, procedures, or functions from the current database. Please upgrade to Neo4j 4.2+ to fix this.");
        }

        final var propertyKeys = getPropertyKeys(db);
        final var labels = getLabels(db);
        final var relationshipTypes = getRelationshipTypes(db);

        return new Neo4jMetadata(
                functions,
                procedures,
                constraints,
                labels,
                relationshipTypes,
                indexes,
                propertyKeys);
    }

    private List<Neo4jFunctionMetadata> getFunctions(final GraphDatabaseApi db) {
        return db.execute(FUNCTIONS_QUERY).getRows().stream().map(row -> {
            final Neo4jBoltQueryResultRow neo4jRow = (Neo4jBoltQueryResultRow) row;
            final String name = neo4jRow.getValue("name").asString();
            final String signature = neo4jRow.getValue("signature").asString();
            final String description = neo4jRow.getValue("description").asString();
            return new Neo4jFunctionMetadata(name, signature, description);
        }).collect(toList());
    }

    private List<Neo4jProcedureMetadata> getProcedures(final GraphDatabaseApi db) {
        return db.execute(PROCEDURES_QUERY).getRows().stream().map(row -> {
            final Neo4jBoltQueryResultRow neo4jRow = (Neo4jBoltQueryResultRow) row;
            final String name = neo4jRow.getValue("name").asString();
            final String signature = neo4jRow.getValue("signature").asString();
            final String description = neo4jRow.getValue("description").asString();
            return new Neo4jProcedureMetadata(name, signature, description);
        }).collect(toList());
    }

    private List<Neo4jIndexMetadata> getIndexes(final GraphDatabaseApi db) {
        return db.execute(INDEXES_QUERY).getRows().stream().map(row -> {
            final Neo4jBoltQueryResultRow neo4jRow = (Neo4jBoltQueryResultRow) row;
            final String name = neo4jRow.getValue("name").asString();
            final String state = neo4jRow.getValue("state").asString();
            return new Neo4jIndexMetadata(name, state);
        }).collect(toList());
    }

    private List<Neo4jConstraintMetadata> getConstraints(final GraphDatabaseApi db) {
        return db.execute(CONSTRAINTS_QUERY).getRows().stream().map(row -> {
            final Neo4jBoltQueryResultRow neo4jRow = (Neo4jBoltQueryResultRow) row;
            final String name = neo4jRow.getValue("name").asString();
            return new Neo4jConstraintMetadata(name);
        }).collect(toList());
    }

    private List<String> getPropertyKeys(final GraphDatabaseApi db) {
        return db.execute(PROPERTY_KEYS_QUERY).getRows().stream().map(row -> {
            final Neo4jBoltQueryResultRow neo4jRow = (Neo4jBoltQueryResultRow) row;
            return neo4jRow.getValue("propertyKey").asString();
        }).collect(toList());
    }

    private List<Neo4jLabelMetadata> getLabels(final GraphDatabaseApi db) {
        return db.execute(LABELS_QUERY).getRows().stream().map(row -> {
            final Neo4jBoltQueryResultRow neo4jRow = (Neo4jBoltQueryResultRow) row;
            final String name = neo4jRow.getValue("labelName").asString();
            final long count = neo4jRow.getValue("labelCount").asLong();
            return new Neo4jLabelMetadata(name, count);
        }).collect(toList());
    }

    private List<Neo4jRelationshipTypeMetadata> getRelationshipTypes(final GraphDatabaseApi db) {
        return db.execute(RELATIONSHIP_TYPES_QUERY).getRows().stream().map(row -> {
            final Neo4jBoltQueryResultRow neo4jRow = (Neo4jBoltQueryResultRow) row;
            final String name = neo4jRow.getValue("relationshipTypeName").asString();
            final long count = neo4jRow.getValue("relationshipTypeCount").asLong();
            return new Neo4jRelationshipTypeMetadata(name, count);
        }).collect(toList());
    }
}
