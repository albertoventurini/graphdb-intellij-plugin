/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console.graph;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.database.DiffService;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.interactions.EditEntityDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.albertoventurini.graphdbplugin.database.api.data.GraphNode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class NodeEditAction extends AnAction {

    private DataSourceApi dataSource;
    private GraphNode node;

    NodeEditAction(String title, String description, Icon icon, DataSourceApi dataSource, GraphNode node) {
        super(title, description, icon);
        this.dataSource = dataSource;
        this.node = node;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = getEventProject(e);
        EditEntityDialog dialog = new EditEntityDialog(project, node);
        if (dialog.showAndGet()) {
            DiffService diffService = new DiffService(project);
            diffService.updateNode(dataSource, node, dialog.getUpdatedEntity());
        }
    }

}
