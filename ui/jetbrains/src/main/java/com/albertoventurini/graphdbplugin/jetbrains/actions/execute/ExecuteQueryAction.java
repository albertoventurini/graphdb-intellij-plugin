/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.actions.execute;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourcesComponent;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.intellij.openapi.actionSystem.ActionButtonComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.util.messages.MessageBus;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.ConsoleToolWindow;
import com.albertoventurini.graphdbplugin.jetbrains.util.NameUtil;
import com.albertoventurini.graphdbplugin.platform.GraphConstants;

import java.awt.*;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.nonNull;

public class ExecuteQueryAction extends QueryAction {
    public ExecuteQueryAction() {
    }

    public ExecuteQueryAction(final PsiElement element) {
        super(element);
    }

    protected void actionPerformed(
            final AnActionEvent e,
            final Project project,
            final Editor editor,
            final String query,
            final Map<String, Object> parameters) {
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        MessageBus messageBus = project.getMessageBus();
        DataSourcesComponent dataSourcesComponent = project.getService(DataSourcesComponent.class);

        final String decoratedQuery = decorateQuery(query);

        ExecuteQueryPayload executeQueryPayload = new ExecuteQueryPayload(decoratedQuery, parameters, editor);
        ConsoleToolWindow.ensureOpen(project);

        if (nonNull(virtualFile)) {
            String fileName = virtualFile.getName();
            if (fileName.startsWith(GraphConstants.BOUND_DATA_SOURCE_PREFIX)) {
                Optional<? extends DataSourceApi> boundDataSource = dataSourcesComponent.getDataSourceContainer()
                        .findDataSource(NameUtil.extractDataSourceUUID(fileName));
                if (boundDataSource.isPresent()) {
                    executeQuery(messageBus, boundDataSource.get(), executeQueryPayload);
                    return;
                }
            }
        }

        ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                "Choose Data Source",
                new ChooseDataSourceActionGroup(messageBus, dataSourcesComponent, executeQueryPayload),
                e.getDataContext(),
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                false
        );

        Component eventComponent = e.getInputEvent().getComponent();
        if (eventComponent instanceof ActionButtonComponent) {
            popup.showUnderneathOf(eventComponent);
        } else {
            popup.showInBestPositionFor(e.getDataContext());
        }
    }

    private void executeQuery(MessageBus messageBus, DataSourceApi dataSource, ExecuteQueryPayload payload) {
        ExecuteQueryEvent executeQueryEvent = messageBus.syncPublisher(ExecuteQueryEvent.EXECUTE_QUERY_TOPIC);
        executeQueryEvent.executeQuery(dataSource, payload);
    }

    protected String decorateQuery(String query) {
        return query;
    }
}
