/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourceDescription;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourceType;

import java.util.Map;

public interface DataSourceApi {

    String getUUID();

    String getName();

    DataSourceType getDataSourceType();

    Map<String, String> getConfiguration();

    default DataSourceDescription getDescription() {
        if (getDataSourceType() == DataSourceType.NEO4J_BOLT) {
            return DataSourceDescription.NEO4J_BOLT;
        }
        throw new IllegalStateException("Unknown data source type encountered: " + getDataSourceType());
    }
}
