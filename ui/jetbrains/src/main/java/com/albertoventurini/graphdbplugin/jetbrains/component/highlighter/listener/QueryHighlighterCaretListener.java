/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.highlighter.listener;

import com.albertoventurini.graphdbplugin.jetbrains.component.highlighter.SyncedElementHighlighter;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;

public class QueryHighlighterCaretListener implements CaretListener {

    private final SyncedElementHighlighter syncedElementHighlighter;

    public QueryHighlighterCaretListener(SyncedElementHighlighter syncedElementHighlighter) {
        this.syncedElementHighlighter = syncedElementHighlighter;
    }

    @Override
    public void caretPositionChanged(CaretEvent e) {
        processEvent(e);
    }

    @Override
    public void caretAdded(CaretEvent e) {
        processEvent(e);
    }

    @Override
    public void caretRemoved(CaretEvent e) {
        processEvent(e);
    }

    private void processEvent(CaretEvent e) {
        Editor editor = e.getEditor();
        Project project = editor.getProject();
        if (project == null) {
            return;
        }

        Document document = editor.getDocument();
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);

        syncedElementHighlighter.highlightStatement(editor, psiFile);
    }
}
