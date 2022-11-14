/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.model;

import com.albertoventurini.graphdbplugin.database.api.data.NoIdGraphEntity;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.dto.ContextMenu;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.graph.EntityContextMenu;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.TreeNodeModelApi;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class RootObjectAwareModel implements TreeNodeModelApi {

    private DataSourceApi dataSourceApi;
    private Object rootObject;

    public RootObjectAwareModel(DataSourceApi dataSourceApi, Object rootObject) {
        this.dataSourceApi = dataSourceApi;
        this.rootObject = rootObject;
    }

    @Override
    public Optional<ContextMenu> getContextMenu() {
        if (rootObject instanceof NoIdGraphEntity) {
            return Optional.of(new EntityContextMenu(dataSourceApi, (NoIdGraphEntity) rootObject));
        } else if (this instanceof NoIdGraphEntity) {
            return Optional.of(new EntityContextMenu(dataSourceApi, (NoIdGraphEntity) this));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Object> getRootObjectValue() {
        return Optional.of(rootObject);
    }

    @Nullable
    @Override
    public DataSourceApi getDataSourceApi() {
        return dataSourceApi;
    }
}
