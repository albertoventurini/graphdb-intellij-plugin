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
import org.jetbrains.annotations.Nullable;

public class CypherFunctionElement implements
        CypherElement,
        CypherElementWithSignature,
        CypherElementWithDocumentation {

    private final String name;
    @Nullable
    private final String description;
    private final InvokableInformation invokableInformation;
    private String documentation;

    public CypherFunctionElement(String name, String signature, @Nullable String description) {
        this.name = name;
        this.description = description;
        this.invokableInformation = new InvokableInformation(signature, name);
    }

    public String getName() {
        return name;
    }

    @Override
    public InvokableInformation getInvokableInformation() {
        return invokableInformation;
    }

    @Override
    public String getDocumentation() {
        if (documentation == null) {
            documentation = ""
                    + "function <b>" + name + "</b><br>"
                    + "Arguments:<br>"
                    + "&nbsp;&nbsp;&nbsp;&nbsp;" + invokableInformation.getSignature() + "<br>"
                    + "Return:<br>"
                    + "&nbsp;&nbsp;&nbsp;&nbsp;" + invokableInformation.getReturnTypeString();

            if (description != null) {
                documentation += "<br><br>"
                        + description;
            }
        }
        return documentation;
    }

    @Override
    public LookupElement getLookupElement() {
        return LookupElementBuilder
                .create(name)
                .bold()
                .withIcon(GraphIcons.Nodes.FUNCTION)
                .withTailText(invokableInformation.getSignature())
                .withTypeText(invokableInformation.getReturnTypeString())
                .withInsertHandler(ParenthesesInsertHandler.getInstance(invokableInformation.hasParameters()));
    }
}
