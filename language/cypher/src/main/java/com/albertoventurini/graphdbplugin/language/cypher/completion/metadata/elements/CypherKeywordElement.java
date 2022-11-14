/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;

public class CypherKeywordElement implements CypherElement {

    private final String keyword;

    public CypherKeywordElement(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public LookupElement getLookupElement() {
        return LookupElementBuilder
                        .create(keyword)
                        .bold();
    }
}
