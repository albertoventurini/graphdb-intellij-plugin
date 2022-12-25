/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.completion;

import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util.BaseCompletionTest;
import com.intellij.codeInsight.lookup.Lookup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserFunctionCompletionTest extends BaseCompletionTest {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        dataSource().neo4j52();
    }

    public void testContainsTestUserFunction() throws Exception {
        myFixture.configureByText("test.cyp", "RETURN xml<caret>");
        myFixture.completeBasic();
        List<String> strings = myFixture.getLookupElementStrings();
        assertThat(strings)
                .contains(
                        "com.albertoventurini.graphdbplugin.test.database.neo4j_4_4.firstTestFunction",
                        "com.albertoventurini.graphdbplugin.test.database.neo4j_4_4.secondTestFunction"
                );
    }

    public void testCompletionCaretAfterParentheses() throws Exception {
        myFixture.configureByText("test.cyp", "RETURN firstTestFuncti<caret>");
        myFixture.completeBasic();
        myFixture.finishLookup(Lookup.REPLACE_SELECT_CHAR);
        myFixture.checkResult("RETURN com.albertoventurini.graphdbplugin.test.database.neo4j_4_4.firstTestFunction()<caret>");
    }

    public void testCompletionCaretAtParentheses() throws Exception {
        myFixture.configureByText("test.cyp", "RETURN secondTestFuncti<caret>");
        myFixture.completeBasic();
        myFixture.finishLookup(Lookup.REPLACE_SELECT_CHAR);
        myFixture.checkResult("RETURN com.albertoventurini.graphdbplugin.test.database.neo4j_4_4.secondTestFunction(<caret>)");
    }
}
