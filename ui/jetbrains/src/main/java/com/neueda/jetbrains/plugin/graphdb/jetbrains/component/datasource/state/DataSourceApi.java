package com.neueda.jetbrains.plugin.graphdb.jetbrains.component.datasource.state;

import com.neueda.jetbrains.plugin.graphdb.jetbrains.component.datasource.DataSourceDescription;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.component.datasource.DataSourceType;

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
