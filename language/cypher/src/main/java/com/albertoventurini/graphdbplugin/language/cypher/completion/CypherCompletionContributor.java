/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.completion;

import com.albertoventurini.graphdbplugin.language.cypher.completion.providers.*;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;

/**
 * Entry point for Cypher auto completion.
 */
class CypherCompletionContributor extends CompletionContributor {

    CypherCompletionContributor() {
        extend(CompletionType.BASIC,
                KeywordCompletionProvider.PATTERN,
                new KeywordCompletionProvider());

        extend(CompletionType.BASIC,
                BuiltInFunctionCompletionProvider.PATTERN,
                new BuiltInFunctionCompletionProvider());

        extend(CompletionType.BASIC,
                LabelsCompletionProvider.PATTERN,
                new LabelsCompletionProvider());

        extend(CompletionType.BASIC,
                RelationshipTypeCompletionProvider.PATTERN,
                new RelationshipTypeCompletionProvider());

        extend(CompletionType.BASIC,
                PropertyKeyCompletionProvider.PATTERN,
                new PropertyKeyCompletionProvider());

        extend(CompletionType.BASIC,
                ProceduresCompletionProvider.PATTERN,
                new ProceduresCompletionProvider());

        extend(CompletionType.BASIC,
                UserFunctionsCompletionProvider.PATTERN,
                new UserFunctionsCompletionProvider());
    }
}
