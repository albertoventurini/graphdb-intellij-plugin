package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata;

import java.util.List;

public record Neo4jIndexMetadata(
        String name,
        String state) { }