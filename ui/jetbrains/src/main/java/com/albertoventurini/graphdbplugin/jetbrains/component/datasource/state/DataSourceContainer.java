/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state;

import java.util.*;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourceType;

public interface DataSourceContainer {

    List getGenericDataSources();

    @SuppressWarnings("unchecked")
    default List<DataSourceApi> getDataSources() {
        return (List<DataSourceApi>) getGenericDataSources();
    }

    Optional<DataSourceApi> findDataSource(String uuid);

    void addDataSource(DataSourceApi dataSource);

    void updateDataSource(DataSourceApi oldDataSource, DataSourceApi newDataSource);

    void removeDataSources(List<DataSourceApi> dataSourcesForRemoval);

    Optional<DataSourceApi> getDataSource(String dataSourceName);

    boolean isDataSourceExists(String dataSourceName);

    DataSourceApi createDataSource(DataSourceApi dataSourceToEdit, DataSourceType dataSourceType,
                                   String dataSourceName, Map<String, String> configuration);
}
