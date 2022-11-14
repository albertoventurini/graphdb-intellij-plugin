/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.highlighter.listener;

import com.albertoventurini.graphdbplugin.jetbrains.component.highlighter.SyncedElementHighlighter;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;

/**
 * Not works as expected. Disabled.
 */
public class QueryHighlighterDocumentListener implements DocumentListener {

    private final SyncedElementHighlighter syncedElementHighlighter;
    private final EditorFactory editorFactory;

    public QueryHighlighterDocumentListener(SyncedElementHighlighter syncedElementHighlighter, EditorFactory editorFactory) {
        this.syncedElementHighlighter = syncedElementHighlighter;
        this.editorFactory = editorFactory;
    }

    @Override
    public void beforeDocumentChange(DocumentEvent event) {
    }

    @Override
    public void documentChanged(DocumentEvent event) {
        Document document = event.getDocument();
        Editor[] editors = editorFactory.getEditors(document);
        for (Editor editor : editors) {
            Project project = editor.getProject();
            if (project == null) {
                return;
            }
            PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
            syncedElementHighlighter.highlightStatement(editor, psiFile);
        }
    }
}
