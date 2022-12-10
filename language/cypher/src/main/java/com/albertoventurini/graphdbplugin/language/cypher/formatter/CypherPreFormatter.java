/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.formatter;

import com.albertoventurini.graphdbplugin.language.cypher.formatter.converter.AbstractCypherConverter;
import com.albertoventurini.graphdbplugin.language.cypher.formatter.converter.FunctionCaseConverter;
import com.albertoventurini.graphdbplugin.language.cypher.formatter.converter.KeywordCaseConverter;
import com.albertoventurini.graphdbplugin.language.cypher.formatter.converter.QuotesConverter;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.codeStyle.PreFormatProcessor;
import com.intellij.util.DocumentUtil;
import com.albertoventurini.graphdbplugin.language.cypher.CypherLanguage;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CypherPreFormatter implements PreFormatProcessor {

    @NotNull
    @Override
    public TextRange process(@NotNull ASTNode element, @NotNull TextRange range) {
        PsiElement psiElement = element.getPsi();
        if (psiElement != null && psiElement.isValid()
                && psiElement.getLanguage().is(CypherLanguage.INSTANCE)) {
            FormatterTask converter = new FormatterTask(psiElement, range);

            if (converter.getDocument() != null) {
                DocumentUtil.executeInBulk(converter.getDocument(), true, converter);
            }

            return converter.getTextRange();
        }
        return range;
    }

    public static class FormatterTask implements Runnable {
        private final Document myDocument;
        private final PsiDocumentManager myDocumentManager;
        private final PsiElement myElement;
        private final TextRange myTextRange;
        private int delta = 0;

        public FormatterTask(@NotNull PsiElement element,
                             @NotNull TextRange textRange) {
            Project project = element.getProject();
            PsiFile file = element.getContainingFile();
            myElement = element;
            myTextRange = new TextRange(textRange.getStartOffset(), textRange.getEndOffset());
            myDocumentManager = PsiDocumentManager.getInstance(project);
            myDocument = myDocumentManager.getDocument(file);
        }

        TextRange getTextRange() {
            return myTextRange.grown(delta);
        }

        Document getDocument() {
            return myDocument;
        }

        public int getDelta() {
            return delta;
        }

        @Override
        public void run() {
            if (myDocument != null) {
                for (AbstractCypherConverter converter : Arrays.asList(
                        new KeywordCaseConverter(this, myDocument),
                        new FunctionCaseConverter(this, myDocument),
                        new QuotesConverter(this, myDocument)
                )) {
                    myDocumentManager.doPostponedOperationsAndUnblockDocument(myDocument);
                    myElement.accept(converter);
                    delta += converter.getDelta();
                    myDocumentManager.commitDocument(myDocument);
                }
            }
        }
    }

}
