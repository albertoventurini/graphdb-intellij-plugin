/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.dto.ContextMenu;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.dto.DataSourceContextMenu;

import javax.swing.*;
import java.util.Optional;

public class DataSourceTreeNodeModel implements TreeNodeModelApi {

    private DataSourceApi dataSourceApi;
    private DataSourceContextMenu dataSourceContextMenu;

    public DataSourceTreeNodeModel(DataSourceApi dataSourceApi) {
        this.dataSourceApi = dataSourceApi;
        this.dataSourceContextMenu = new DataSourceContextMenu(dataSourceApi);
    }

    public Optional<ContextMenu> getContextMenu() {
        return Optional.of(dataSourceContextMenu);
    }

    @Override
    public NodeType getType() {
        return Neo4jTreeNodeType.DATASOURCE;
    }

    @Override
    public Optional<Icon> getIcon() {
        return Optional.ofNullable(dataSourceApi.getDescription().getIcon());
    }

    @Override
    public Optional<String> getText() {
        return Optional.ofNullable(dataSourceApi.getName());
    }

    @Override
    public Optional<Object> getValue() {
        return Optional.empty();
    }

    @Override
    public DataSourceApi getDataSourceApi() {
        return dataSourceApi;
    }
}
