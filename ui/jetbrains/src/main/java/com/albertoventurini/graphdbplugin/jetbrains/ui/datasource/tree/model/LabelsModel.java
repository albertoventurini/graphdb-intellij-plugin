/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.model;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.Neo4jEntityViewNodeType;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.NodeType;

import java.util.Optional;

public class LabelsModel extends RootObjectAwareModel {

    private NodeType type = Neo4jEntityViewNodeType.NODE_LABELS;
    private String text = "labels";
    private String description = "list";

    public LabelsModel(DataSourceApi dataSourceApi, Object rootObject) {
        super(dataSourceApi, rootObject);
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

    @Override
    public String toString() {
        return text;
    }
}
