/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.references.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherStatement;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes;
import com.albertoventurini.graphdbplugin.language.cypher.references.CypherReferenceBase;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveResult;
import com.albertoventurini.graphdbplugin.language.cypher.util.CypherUtil;
import com.albertoventurini.graphdbplugin.language.cypher.util.PsiTraversalUtilities;
import icons.GraphIcons;
import org.jetbrains.annotations.NotNull;

/**
 * Cypher identifier reference. Responsible for providing references and handle renaming.
 *
 * @author dmitry@vrublevsky.me
 */
public class CypherVariableReference extends CypherReferenceBase {

    public CypherVariableReference(@NotNull PsiElement element, TextRange textRange) {
        super(element, textRange);
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        PsiElement statementElement = PsiTraversalUtilities.getParentOfType(myElement, CypherStatement.class);
        return resolveResults(CypherUtil.findAllByName(statementElement, CypherTypes.VARIABLE, name));
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return uniqueVariants(CypherUtil.findAll(myElement.getContainingFile(), CypherTypes.VARIABLE)).stream()
                .map(cypherIdentifier -> LookupElementBuilder.create(cypherIdentifier)
                        .withIcon(GraphIcons.Nodes.VARIABLE)
                        .withTypeText("variable"))
                .toArray(Object[]::new);
    }
}
