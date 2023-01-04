package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourceMetadata;

import java.util.*;

public record Neo4jMetadata(
        List<Neo4jFunctionMetadata> functions,
        List<Neo4jProcedureMetadata> procedures,
        List<Neo4jConstraintMetadata> constraints,
        List<Neo4jLabelMetadata> labels,
        List<Neo4jRelationshipTypeMetadata> relationshipTypes,
        List<Neo4jIndexMetadata> indexes,
        List<String> propertyKeys)
        implements DataSourceMetadata { }
