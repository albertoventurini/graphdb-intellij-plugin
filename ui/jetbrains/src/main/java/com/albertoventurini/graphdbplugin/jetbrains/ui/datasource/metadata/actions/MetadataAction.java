/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.actions;

import com.albertoventurini.graphdbplugin.jetbrains.actions.execute.ExecuteQueryEvent;
import com.albertoventurini.graphdbplugin.jetbrains.actions.execute.ExecuteQueryPayload;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourcesComponent;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.util.messages.MessageBus;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.ConsoleToolWindow;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.event.OpenTabEvent;

import javax.swing.*;
import java.util.Optional;

import static com.albertoventurini.graphdbplugin.platform.GraphConstants.ToolWindow.*;

public abstract class MetadataAction extends AnAction {

    private String data;
    private String dataSourceUuid;

    MetadataAction(String data, String dataSourceUuid, String title, String description, Icon icon) {
        super(title, description, icon);
        this.data = data;
        this.dataSourceUuid = dataSourceUuid;
    }

    protected abstract String getQuery(String data);

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = getEventProject(e);
        MessageBus messageBus = project.getMessageBus();

        ExecuteQueryEvent executeQueryEvent = messageBus.syncPublisher(ExecuteQueryEvent.EXECUTE_QUERY_TOPIC);

        ExecuteQueryPayload payload = new ExecuteQueryPayload(getQuery(data));

        DataSourcesComponent dataSourcesComponent =
                project.getService(DataSourcesComponent.class);
        Optional<DataSourceApi> dataSource = dataSourcesComponent.getDataSourceContainer().findDataSource(dataSourceUuid);

        dataSource.ifPresent(dataSourceApi -> executeQueryEvent.executeQuery(dataSourceApi, payload));

        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(CONSOLE_TOOL_WINDOW);
        if (!toolWindow.isVisible()) {
            ConsoleToolWindow.ensureOpen(project);
            messageBus.syncPublisher(OpenTabEvent.OPEN_TAB_TOPIC).openTab(Tabs.TABLE);
        }
    }
}
