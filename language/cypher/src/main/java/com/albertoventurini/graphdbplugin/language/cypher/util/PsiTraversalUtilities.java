/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.util;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherStatementItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public final class PsiTraversalUtilities {

    public static PsiElement getParentOfType(PsiElement element, Class<?> type) {
        if (element == null) {
            return null;
        }
        if (type.isAssignableFrom(element.getClass())) {
            return element;
        }
        return getParentOfType(element.getParent(), type);
    }

    public static final class Cypher {

        public static PsiElement getCypherStatementAtOffset(PsiFile psiFile, int offset) {
            PsiElement elementAtCaret = psiFile.findElementAt(offset == 0 ? offset : offset - 1);
            return PsiTraversalUtilities.getParentOfType(elementAtCaret, CypherStatementItem.class);
        }

        public static PsiElement getCypherStatement(PsiElement element) {
            return PsiTraversalUtilities.getParentOfType(element, CypherStatementItem.class);
        }

    }
}
