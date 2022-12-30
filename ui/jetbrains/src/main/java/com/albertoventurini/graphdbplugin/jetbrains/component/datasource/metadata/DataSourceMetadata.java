/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j.Neo4jConstraintMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j.Neo4jFunctionMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j.Neo4jIndexMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j.Neo4jProcedureMetadata;

import java.util.List;

public interface DataSourceMetadata {

    List<Neo4jFunctionMetadata> getFunctions();

    List<Neo4jProcedureMetadata> getProcedures();

    List<Neo4jIndexMetadata> getIndexes();

    List<Neo4jConstraintMetadata> getConstraints();
}
