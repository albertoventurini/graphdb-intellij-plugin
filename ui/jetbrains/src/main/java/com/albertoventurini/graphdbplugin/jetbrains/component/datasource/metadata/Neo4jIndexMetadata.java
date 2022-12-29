package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata;

import java.util.List;

public record Neo4jIndexMetadata(
        String name,
        String state,
        double populationPercent,
        String type,
        String entityType,
        List<String> labelsOrTypes,
        List<String> properties) { }
