/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.model;

import com.albertoventurini.graphdbplugin.database.api.data.GraphRelationship;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.dto.ContextMenu;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.graph.EntityContextMenu;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.Neo4jEntityViewNodeType;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.NodeType;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.TreeNodeModelApi;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Optional;

public class RelationshipModel implements TreeNodeModelApi {

    private NodeType type = Neo4jEntityViewNodeType.RELATIONSHIP;
    private GraphRelationship relationship;
    private String text;
    private String description = "relationship";
    private DataSourceApi dataSourceApi;

    public RelationshipModel(GraphRelationship relationship, String text, DataSourceApi dataSourceApi) {
        this.relationship = relationship;
        this.text = text;
        this.dataSourceApi = dataSourceApi;
    }

    @Override
    public Optional<ContextMenu> getContextMenu() {
        return Optional.of(new EntityContextMenu(dataSourceApi, relationship));
    }

    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public Optional<Icon> getIcon() {
        return Optional.empty();
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
    public Optional<Object> getValue() {
        return Optional.of(relationship);
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
