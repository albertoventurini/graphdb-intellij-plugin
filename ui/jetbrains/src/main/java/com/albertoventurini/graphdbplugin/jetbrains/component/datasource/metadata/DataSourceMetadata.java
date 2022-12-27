/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata;

import java.util.List;
import java.util.Map;

public interface DataSourceMetadata {

    List<Map<String, String>> getMetadata(String metadataKey);

    List<Neo4jFunctionMetadata> getFunctions();

    List<Neo4jProcedureMetadata> getProcedures();

    boolean isMetadataExists(String metadataKey);
}
