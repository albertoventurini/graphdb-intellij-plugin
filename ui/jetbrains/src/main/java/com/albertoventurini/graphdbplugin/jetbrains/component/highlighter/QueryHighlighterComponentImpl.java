/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.highlighter;

import com.intellij.openapi.Disposable;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.EditorFactory;
import com.albertoventurini.graphdbplugin.jetbrains.component.highlighter.listener.QueryHighlighterCaretListener;
import com.albertoventurini.graphdbplugin.jetbrains.component.highlighter.listener.QueryHighlighterDocumentListener;

public class QueryHighlighterComponentImpl implements QueryHighlighterComponent, Disposable {

    private final QueryHighlighterCaretListener queryHighlighterCaretListener;
    private final QueryHighlighterDocumentListener queryHighlighterDocumentListener;
    private final SyncedElementHighlighter syncedElementHighlighter;

    public QueryHighlighterComponentImpl() {
        EditorFactory editorFactory = ApplicationManager.getApplication().getComponent(EditorFactory.class);

        syncedElementHighlighter = new SyncedElementHighlighter();
        queryHighlighterCaretListener = new QueryHighlighterCaretListener(syncedElementHighlighter);
        queryHighlighterDocumentListener = new QueryHighlighterDocumentListener(syncedElementHighlighter, editorFactory);

        editorFactory.getEventMulticaster().addCaretListener(queryHighlighterCaretListener, this);
        editorFactory.getEventMulticaster().addDocumentListener(queryHighlighterDocumentListener, this);
    }

    @Override
    public void dispose() {
        EditorFactory editorFactory = ApplicationManager.getApplication().getComponent(EditorFactory.class);
        if (queryHighlighterCaretListener != null) {
            editorFactory.getEventMulticaster().removeCaretListener(queryHighlighterCaretListener);
        }
        if (queryHighlighterDocumentListener != null) {
            editorFactory.getEventMulticaster().removeDocumentListener(queryHighlighterDocumentListener);
        }
        if (syncedElementHighlighter != null) {
            syncedElementHighlighter.dispose();
        }
    }
}
