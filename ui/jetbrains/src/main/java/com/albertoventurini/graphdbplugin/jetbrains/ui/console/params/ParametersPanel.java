/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console.params;

import com.albertoventurini.graphdbplugin.jetbrains.ui.console.GraphConsoleView;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.event.QueryParametersRetrievalErrorEvent;
import com.albertoventurini.graphdbplugin.jetbrains.util.FileUtil;
import com.google.common.base.Throwables;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.icons.AllIcons;
import com.intellij.json.JsonFileType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;

import javax.swing.*;
import java.awt.*;

import static com.albertoventurini.graphdbplugin.jetbrains.ui.console.event.QueryParametersRetrievalErrorEvent.PARAMS_ERROR_COMMON_MSG;

public class ParametersPanel implements ParametersProvider {

    private static final FileDocumentManager FILE_DOCUMENT_MANAGER = FileDocumentManager.getInstance();
    public static final Icon ICON_HELP = AllIcons.Actions.Help;

    private Editor globalParamEditor;
    private GraphConsoleView graphConsoleView;
    private MessageBus messageBus;
    private ParametersService service;

    public void initialize(GraphConsoleView graphConsoleView, Project project) {
        this.graphConsoleView = graphConsoleView;
        this.messageBus = project.getMessageBus();
        this.service = project.getService(ParametersService.class);
        setupEditor(project);
    }

    public String getGlobalParametersJson() {
        return globalParamEditor.getDocument().getText();
    }

    private void initializeUi() {
        graphConsoleView.getParametersTab().add(globalParamEditor.getComponent(), BorderLayout.CENTER);
        service.registerParametersProvider(this);
        MessageBusConnection mbConnection = messageBus.connect();
        mbConnection.subscribe(QueryParametersRetrievalErrorEvent.QUERY_PARAMETERS_RETRIEVAL_ERROR_EVENT_TOPIC,
                (QueryParametersRetrievalErrorEvent) (exception, editor) -> {
                    if (editor == null) {
                        return;
                    }
                    String errorMessage;
                    if (exception.getMessage() != null) {
                        errorMessage = String.format("%s: %s", PARAMS_ERROR_COMMON_MSG, exception.getMessage());
                    } else {
                        errorMessage = PARAMS_ERROR_COMMON_MSG;
                    }
                    HintManager.getInstance().showErrorHint(editor, errorMessage);
                });
    }

    private void setupEditor(Project project) {
        ApplicationManager.getApplication().runWriteAction(() -> {
            try {
                VirtualFile file = FileUtil.getScratchFile(project, "Neo4jGraphDbConsoleParametersPanel.json");
                Document document = FILE_DOCUMENT_MANAGER.getDocument(file);
                globalParamEditor = createEditor(project, document);
                JLabel jLabel = new JLabel("<html>Query parameters:</html>");
                jLabel.setIcon(ICON_HELP);
                jLabel.setToolTipText("Enter parameters in JSON format. Will be applied to any data source when executed");
                globalParamEditor.setHeaderComponent(jLabel);
                setInitialContent(document);

                initializeUi();
            } catch (Throwable e) {
                Throwables.throwIfUnchecked(e);
                throw new RuntimeException(e);
            }
        });
    }

    private void setInitialContent(Document document) {
        if (document != null && document.getText().isEmpty()) {
            final Runnable setTextRunner = () -> document.setText("{}");
            ApplicationManager.getApplication()
                    .invokeLater(() -> ApplicationManager.getApplication().runWriteAction(setTextRunner));
        }
    }

    private static Editor createEditor(Project project, Document document) {
        return EditorFactory.getInstance().createEditor(document, project, JsonFileType.INSTANCE, false);
    }
}
