/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.actions;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.database.DiffService;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.interactions.EditEntityDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class CreateNodeAction extends AnAction {
    private final DataSourceApi dataSourceApi;

    public CreateNodeAction(String title, DataSourceApi dataSourceApi) {
        super(title, null, null);
        this.dataSourceApi = dataSourceApi;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        EditEntityDialog dialog = new EditEntityDialog(e.getProject(), null);
        if (dialog.showAndGet()) {
            DiffService diffService = new DiffService(e.getProject());
            diffService.saveNewNode(dataSourceApi, dialog.getUpdatedEntity());
        }
    }
}
