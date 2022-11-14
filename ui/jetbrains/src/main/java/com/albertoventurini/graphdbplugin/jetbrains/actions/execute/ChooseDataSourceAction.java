/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.actions.execute;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourcesComponent;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.util.messages.MessageBus;

public class ChooseDataSourceAction extends AnAction {

    private final DataSourceApi dataSource;
    private final MessageBus messageBus;
    private final ExecuteQueryPayload executeQueryPayload;

    public ChooseDataSourceAction(DataSourceApi dataSource, DataSourcesComponent component,
                                  MessageBus messageBus, ExecuteQueryPayload executeQueryPayload) {
        super(dataSource.getName(), null, dataSource.getDescription().getIcon());
        this.dataSource = dataSource;
        this.messageBus = messageBus;
        this.executeQueryPayload = executeQueryPayload;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        ExecuteQueryEvent executeQueryEvent = messageBus.syncPublisher(ExecuteQueryEvent.EXECUTE_QUERY_TOPIC);
        Editor editor = e.getData(CommonDataKeys.EDITOR_EVEN_IF_INACTIVE);
        executeQueryEvent.executeQuery(dataSource, executeQueryPayload);
    }
}
