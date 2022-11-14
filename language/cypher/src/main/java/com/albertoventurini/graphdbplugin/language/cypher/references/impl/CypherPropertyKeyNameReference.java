/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.references.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes;
import com.albertoventurini.graphdbplugin.language.cypher.references.CypherReferenceBase;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveResult;
import com.albertoventurini.graphdbplugin.language.cypher.util.CypherUtil;
import icons.GraphIcons;
import org.jetbrains.annotations.NotNull;

public class CypherPropertyKeyNameReference extends CypherReferenceBase {

    public CypherPropertyKeyNameReference(@NotNull PsiElement element, TextRange textRange) {
        super(element, textRange);
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        return resolveResults(CypherUtil.findAllByName(myElement.getContainingFile(), CypherTypes.PROPERTY_KEY_NAME, name));
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return uniqueVariants(CypherUtil.findAll(myElement.getProject(), CypherTypes.PROPERTY_KEY_NAME)).stream()
                .map(cypherPropertyKeyName -> LookupElementBuilder.create(cypherPropertyKeyName)
                        .withIcon(GraphIcons.Nodes.PROPERTY_KEY)
                        .withTypeText("property (reference)"))
                .toArray(Object[]::new);
    }
}
