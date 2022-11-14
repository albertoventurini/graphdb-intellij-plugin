/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console.graph;

import com.albertoventurini.graphdbplugin.jetbrains.actions.execute.ExecuteQueryPayload;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.database.QueryExecutionService;
import com.google.common.collect.ImmutableMap;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.albertoventurini.graphdbplugin.database.api.data.GraphRelationship;

import javax.swing.*;

public class RelationshipDeleteAction extends AnAction {

    private DataSourceApi dataSource;
    private GraphRelationship relationship;

    RelationshipDeleteAction(String title, String description, Icon icon, DataSourceApi dataSource, GraphRelationship relationship) {
        super(title, description, icon);
        this.dataSource = dataSource;
        this.relationship = relationship;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = getEventProject(e);
        if (project != null) {
            QueryExecutionService service = new QueryExecutionService(project.getMessageBus());

            service.executeQuery(dataSource, new ExecuteQueryPayload("MATCH ()-[n]->() WHERE ID(n) = $id DELETE n",
                    ImmutableMap.of("id", Long.parseLong(relationship.getId())),
                    null));
        }
    }
}
