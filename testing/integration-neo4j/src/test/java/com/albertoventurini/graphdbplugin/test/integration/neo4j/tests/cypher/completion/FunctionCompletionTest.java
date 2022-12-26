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

public class FunctionCompletionTest extends BaseCompletionTest {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        dataSource().neo4j52();
    }

    public void testContainsApocFunction() {
        myFixture.configureByText("test.cyp", "RETURN xml<caret>");
        myFixture.completeBasic();
        List<String> strings = myFixture.getLookupElementStrings();
        assertThat(strings).contains("apoc.xml.parse");
    }

    public void testCompletionCaretAfterParentheses() {
        myFixture.configureByText("test.cyp", "RETURN version<caret>");
        myFixture.completeBasic();
        myFixture.finishLookup(Lookup.REPLACE_SELECT_CHAR);
        myFixture.checkResult("RETURN apoc.version()<caret>");
    }

    public void testCompletionCaretAtParentheses() {
        myFixture.configureByText("test.cyp", "RETURN uuidHexToBase64<caret>");
        myFixture.completeBasic();
        myFixture.finishLookup(Lookup.REPLACE_SELECT_CHAR);
        myFixture.checkResult("RETURN apoc.create.uuidHexToBase64(<caret>)");
    }

    public void testBuiltInFunction() {
        myFixture.configureByText("test.cyp", "RETURN toStrin<caret>");
        myFixture.completeBasic();
        myFixture.finishLookup(Lookup.REPLACE_SELECT_CHAR);
        myFixture.checkResult("RETURN toString(<caret>)");
    }

//    public void testFunctionWithNamespace() {
//        myFixture.configureByText("test.cyp", "RETURN apoc.vers<caret>");
//        myFixture.completeBasic();
//        myFixture.finishLookup(Lookup.REPLACE_SELECT_CHAR);
//        myFixture.checkResult("RETURN apoc.version()<caret>");
//    }
}
