/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.actions.execute;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourcesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.psi.PsiFile;
import com.intellij.util.messages.MessageBus;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.ConsoleToolWindow;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.params.ParametersService;
import com.albertoventurini.graphdbplugin.jetbrains.util.Notifier;
import com.albertoventurini.graphdbplugin.language.cypher.file.CypherFileType;
import org.jetbrains.annotations.NotNull;

public class ExecuteAllAction extends AnAction {
    @Override
    public void update(AnActionEvent e) {
        PsiFile psiFile = e.getDataContext().getData(PlatformDataKeys.PSI_FILE);
        if (psiFile != null && psiFile.getFileType() instanceof CypherFileType) {
            e.getPresentation().setEnabled(true);
        } else {
            e.getPresentation().setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = getEventProject(e);
        if (project == null) {
            Notifier.error("Query execution error", "No project present.");
            return;
        }
        MessageBus messageBus = project.getMessageBus();
        ParametersService parameterService = project.getService(ParametersService.class);
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);

        StatementCollector statementCollector = new StatementCollector(messageBus, parameterService);

        // This should never happen
        if (psiFile == null) {
            Notifier.error("Internal error", "No PsiFile present.");
            return;
        }
        psiFile.acceptChildren(statementCollector);
        if (!statementCollector.hasErrors()) {
            ExecuteQueryPayload executeQueryPayload =
                new ExecuteQueryPayload(statementCollector.getQueries(), statementCollector.getParameters(), psiFile.getName());
            ConsoleToolWindow.ensureOpen(project);

            DataSourcesComponent dataSourcesComponent = project.getComponent(DataSourcesComponent.class);
            ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                "Choose Data Source",
                new ChooseDataSourceActionGroup(messageBus, dataSourcesComponent, executeQueryPayload),
                e.getDataContext(),
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                false
            );
            popup.showInBestPositionFor(e.getDataContext());
        } else {
            Notifier.error("Query execution error", "File contains errors");
        }
    }
}
