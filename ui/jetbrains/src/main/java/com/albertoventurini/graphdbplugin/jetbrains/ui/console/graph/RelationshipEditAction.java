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
import com.albertoventurini.graphdbplugin.database.api.data.GraphRelationship;

import javax.swing.*;

public class RelationshipEditAction extends AnAction {

    private DataSourceApi dataSource;
    private GraphRelationship relationship;

    RelationshipEditAction(String title, String description, Icon icon, DataSourceApi dataSource, GraphRelationship relationship) {
        super(title, description, icon);
        this.dataSource = dataSource;
        this.relationship = relationship;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = getEventProject(e);
        EditEntityDialog dialog = new EditEntityDialog(project, relationship);
        if (dialog.showAndGet()) {
            DiffService diffService = new DiffService(project);
            diffService.updateRelationShip(dataSource, relationship, dialog.getUpdatedEntity());
        }
    }
}
