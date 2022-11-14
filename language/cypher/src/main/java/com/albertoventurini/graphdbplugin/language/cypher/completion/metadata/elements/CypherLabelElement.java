/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import icons.GraphIcons;

public class CypherLabelElement implements CypherElement {

    private final String label;

    public CypherLabelElement(String label) {
        this.label = label;
    }

    @Override
    public LookupElement getLookupElement() {
        return LookupElementBuilder.create(label)
                .withIcon(GraphIcons.Nodes.LABEL)
                .withTypeText("label");
    }

    public String getName() {
        return label;
    }
}
