/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.completion.providers;

import com.albertoventurini.graphdbplugin.language.cypher.CypherParserDefinition;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes;
import org.jetbrains.annotations.NotNull;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.albertoventurini.graphdbplugin.language.cypher.CypherLanguage;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.CypherElement;

public final class ProceduresCompletionProvider extends BaseCompletionProvider {
    public static final ElementPattern<PsiElement> PATTERN = PlatformPatterns
            .psiElement()
            .andNot(PlatformPatterns.psiElement(CypherParserDefinition.LINE_COMMENT))
            .andNot(PlatformPatterns.psiElement(CypherParserDefinition.BLOCK_COMMENT))
            .withLanguage(CypherLanguage.INSTANCE);

    @Override
    protected void addCompletions(
            @NotNull final CompletionParameters parameters,
            @NotNull final ProcessingContext context,
            @NotNull final CompletionResultSet result) {
        withCypherMetadataProvider(parameters, metadataProvider -> metadataProvider.getProcedures().stream()
                .map(CypherElement::getLookupElement)
                .forEach(result::addElement));
    }
}
