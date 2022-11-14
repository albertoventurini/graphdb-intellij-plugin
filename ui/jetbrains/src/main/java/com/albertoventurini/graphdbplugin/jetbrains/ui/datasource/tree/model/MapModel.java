/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.model;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.NodeType;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.TreeNodeModelApi;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.Neo4jEntityViewNodeType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MapModel implements TreeNodeModelApi {

    private NodeType type = Neo4jEntityViewNodeType.NODE_MAP;
    private String text;
    private String description = "map";
    private DataSourceApi dataSourceApi;

    public MapModel(String text, DataSourceApi dataSourceApi) {
        this.text = text;
        this.dataSourceApi = dataSourceApi;
    }

    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public Optional<String> getText() {
        return Optional.of(text);
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of(description);
    }

    @Nullable
    @Override
    public DataSourceApi getDataSourceApi() {
        return dataSourceApi;
    }

    @Override
    public String toString() {
        return text;
    }
}
