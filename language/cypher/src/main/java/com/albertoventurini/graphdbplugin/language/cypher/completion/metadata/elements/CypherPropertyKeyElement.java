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

public class CypherPropertyKeyElement implements CypherElement {

    private final String propertyKey;

    public CypherPropertyKeyElement(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    @Override
    public LookupElement getLookupElement() {
        return LookupElementBuilder.create(propertyKey)
                .withIcon(GraphIcons.Nodes.PROPERTY_KEY)
                .withTypeText("property");
    }

    public String getName() {
        return propertyKey;
    }
}
