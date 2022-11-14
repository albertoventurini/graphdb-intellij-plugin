/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.dto;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.actions.DataSourceActionGroup;

public class DataSourceContextMenu implements ContextMenu {

    private DataSourceApi dataSourceApi;

    public DataSourceContextMenu(DataSourceApi dataSourceApi) {
        this.dataSourceApi = dataSourceApi;
    }

    @Override
    public void showPopup(DataContext dataContext) {
        ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                dataSourceApi.getName(),
                new DataSourceActionGroup(dataSourceApi),
                dataContext,
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                true
        );

        popup.showInBestPositionFor(dataContext);
    }
}
