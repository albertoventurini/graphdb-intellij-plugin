/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.impl;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourceType;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;

import java.util.HashMap;
import java.util.Map;

public class DataSourceV1 implements DataSourceApi {

    public String uuid = "notset";
    public String name = "unknown";
    public DataSourceType dataSourceType = DataSourceType.UNKNOWN;
    public Map<String, String> configuration = new HashMap<>();

    /**
     * Default constructor for serialization.
     */
    public DataSourceV1() {
    }

    public DataSourceV1(String uuid, String name, DataSourceType dataSourceType,
                        Map<String, String> configuration) {
        this.uuid = uuid;
        this.name = name;
        this.dataSourceType = dataSourceType;
        this.configuration = configuration;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    @Override
    public Map<String, String> getConfiguration() {
        return configuration;
    }
}
