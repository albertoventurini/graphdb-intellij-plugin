/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.editor;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherStringLiteral;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class CypherTypedHandlerDelegate extends TypedHandlerDelegate {

    @NotNull
    @Override
    public Result checkAutoPopup(char charTyped, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        final int offset = editor.getCaretModel().getOffset() - 1;
        if (offset >= 0) {
            final PsiElement element = file.findElementAt(offset);
            if (element instanceof CypherStringLiteral) {
                return Result.STOP;
            }
        }
        return charTyped == ':'
                || charTyped == '.'
                || charTyped == '('
                || charTyped == '[' ? Result.CONTINUE : Result.STOP;
    }
}
