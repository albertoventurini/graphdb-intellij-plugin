/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.completion;

import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util.BaseCompletionTest;
import com.intellij.codeInsight.completion.CompletionType;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class KeywordsCompletionTest extends BaseCompletionTest {

    public void testKeywords() throws Exception {
        myFixture.configureByText("test.cyp", "<caret>");
        myFixture.complete(CompletionType.BASIC);
        List<String> strings = myFixture.getLookupElementStrings();
        assertThat(strings)
                .containsAll(CypherKeywords.KEYWORDS);
    }
}
