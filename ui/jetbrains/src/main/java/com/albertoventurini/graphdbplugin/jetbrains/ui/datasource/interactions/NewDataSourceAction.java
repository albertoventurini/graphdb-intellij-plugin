/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.interactions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.DataSourcesView;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public class NewDataSourceAction extends AnAction {

    private final DataSourcesView window;
    private final DataSourceDialog dataSourceDialog;

    public NewDataSourceAction(DataSourcesView dataSourcesView, DataSourceDialog dataSourceDialog,
                               String title, String description, Icon icon) {
        super(title, description, icon);
        this.window = dataSourcesView;
        this.dataSourceDialog = dataSourceDialog;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (dataSourceDialog.go()) {
            window.createDataSource(dataSourceDialog.constructDataSource());
        }
    }
}

