/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.dto.ContextMenu;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Optional;

public interface TreeNodeModelApi {

    NodeType getType();

    default Optional<ContextMenu> getContextMenu() {
        return Optional.empty();
    }

    default Optional<Icon> getIcon() {
        return Optional.empty();
    }

    default Optional<String> getText() {
        return Optional.empty();
    }

    default Optional<String> getDescription() {
        return Optional.empty();
    }

    default Optional<Object> getValue() {
        return Optional.empty();
    }

    default Optional<Object> getRootObjectValue() {
        return Optional.empty();
    }

    @Nullable
    DataSourceApi getDataSourceApi();
}
