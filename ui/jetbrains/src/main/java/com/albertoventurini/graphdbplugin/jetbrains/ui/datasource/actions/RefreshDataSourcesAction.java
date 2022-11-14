/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.AnActionButton;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.DataSourcesView;
import org.jetbrains.annotations.NotNull;

public class RefreshDataSourcesAction extends AnActionButton {

    private final DataSourcesView dataSourcesView;

    public RefreshDataSourcesAction(DataSourcesView dataSourcesView) {
        super("Refresh", "Refresh all data sources", AllIcons.Actions.Refresh);
        this.dataSourcesView = dataSourcesView;
    }

    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        dataSourcesView.refreshDataSourcesMetadata();
    }
}
