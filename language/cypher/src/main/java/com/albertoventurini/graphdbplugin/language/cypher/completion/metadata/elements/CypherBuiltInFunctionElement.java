/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements;

import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import icons.GraphIcons;

public class CypherBuiltInFunctionElement implements CypherElement {

    private final InvokableInformation invokable;

    public CypherBuiltInFunctionElement(InvokableInformation invokable) {
        this.invokable = invokable;
    }

    public InvokableInformation getInvokable() {
        return invokable;
    }

    @Override
    public LookupElement getLookupElement() {
        return LookupElementBuilder
                .create(invokable.getName())
                .bold()
                .withIcon(GraphIcons.Nodes.FUNCTION)
                .withTailText(invokable.getSignature())
                .withTypeText(invokable.getReturnTypeString())
                .withInsertHandler(ParenthesesInsertHandler.getInstance(invokable.hasParameters()));
    }
}
