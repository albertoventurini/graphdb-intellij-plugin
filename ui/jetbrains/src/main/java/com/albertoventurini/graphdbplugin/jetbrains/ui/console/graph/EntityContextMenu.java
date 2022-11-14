/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console.graph;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.dto.ContextMenu;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.albertoventurini.graphdbplugin.database.api.data.NoIdGraphEntity;

public class EntityContextMenu implements ContextMenu {

    private DataSourceApi dataSourceApi;
    private NoIdGraphEntity entity;

    public EntityContextMenu(DataSourceApi dataSourceApi, NoIdGraphEntity entity) {
        this.dataSourceApi = dataSourceApi;
        this.entity = entity;
    }

    @Override
    public void showPopup(DataContext dataContext) {
        ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                entity.getRepresentation(),
                new EntityActionGroup(dataSourceApi, entity),
                dataContext,
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                true
        );

        popup.showInBestPositionFor(dataContext);
    }
}
