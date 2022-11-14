/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.dto;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.actions.MetadataActionGroup;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.NodeType;

public class MetadataContextMenu implements ContextMenu     {

    private NodeType metadataType;
    private DataSourceApi dataSourceApi;
    private String data;

    public MetadataContextMenu(NodeType metadataType, DataSourceApi dataSourceApi, String data) {
        this.metadataType = metadataType;
        this.dataSourceApi = dataSourceApi;
        this.data = data;
    }

    @Override
    public void showPopup(DataContext dataContext) {
        ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                data,
                new MetadataActionGroup(metadataType, data, dataSourceApi.getUUID()),
                dataContext,
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                true
        );

        popup.showInBestPositionFor(dataContext);
    }

    public NodeType getMetadataType() {
        return metadataType;
    }

    public DataSourceApi getDataSourceApi() {
        return dataSourceApi;
    }

    public String getData() {
        return data;
    }
}
