/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.editor;

import com.albertoventurini.graphdbplugin.language.cypher.editor.CypherParameterInfoHandler;
import com.albertoventurini.graphdbplugin.language.cypher.references.CypherInvocation;
import com.intellij.codeInsight.hint.ParameterInfoComponent;
import com.intellij.lang.parameterInfo.CreateParameterInfoContext;
import com.intellij.lang.parameterInfo.ParameterInfoUIContextEx;
import com.intellij.testFramework.utils.parameterInfo.MockCreateParameterInfoContext;
import com.intellij.testFramework.utils.parameterInfo.MockUpdateParameterInfoContext;


import com.albertoventurini.graphdbplugin.test.integration.neo4j.util.base.BaseIntegrationTest;

public class ArgumentHintTest extends BaseIntegrationTest {

    private CypherParameterInfoHandler parameterInfoHandler;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        parameterInfoHandler = new CypherParameterInfoHandler();

        dataSource().neo4j52();
    }

    public void testSpecialAll() {
        doTest("RETURN ALL (x <caret>IN nodes(p) WHERE x.age > 30)",
                "<html>(<b color=1d1d1d>variable :: VARIABLE IN list :: LIST OF ANY? WHERE predicate :: ANY?</b>)</html>");
    }

    public void testBuiltIn() {
        doTest("return toFloat(<caret>\"12\")",
                "<html>(<b color=1d1d1d>input :: STRING?</b>)</html>");
    }

    public void testProcedure() {
        doTest("CALL db.resampleIndex(<caret>\"test\");",
                "<html>(<b color=1d1d1d>indexName :: STRING?</b>)</html>");
    }

    public void testApocFunction() {
        doTest("RETURN apoc.text.capitalize(<caret>\"test\");",
                "<html>(<b color=1d1d1d>text :: STRING?</b>)</html>");
    }

    public void testNoParams() {
        doTest("RETURN e(<caret>)",
                "<html>no parameters</html>");
    }

    public void testUnknownFunction() {
        doTest("return unknown(<caret>)",
            "<html>unknown parameters</html>");
    }

    private void doTest(String query, String expectedHighlight) {
        myFixture.configureByText("test.cyp", query);
        Object[] itemsToShow = getItemsToShow();
        int paramIdx = getHighlightedItem();
        String presentation = getPresentation(itemsToShow, paramIdx);

        assertEquals(1, itemsToShow.length);
        assertEquals(expectedHighlight, presentation);
    }

    private Object[] getItemsToShow() {
        CreateParameterInfoContext createCtx = new MockCreateParameterInfoContext(myFixture.getEditor(), myFixture.getFile());
        CypherInvocation psiElement = parameterInfoHandler.findElementForParameterInfo(createCtx);
        assertNotNull(psiElement);
        parameterInfoHandler.showParameterInfo(psiElement, createCtx);
        return createCtx.getItemsToShow();
    }


    private int getHighlightedItem() {
        MockUpdateParameterInfoContext updateCtx = new MockUpdateParameterInfoContext(myFixture.getEditor(), myFixture.getFile());
        CypherInvocation psiElement = parameterInfoHandler.findElementForUpdatingParameterInfo(updateCtx);
        assertNotNull(psiElement);
        parameterInfoHandler.updateParameterInfo(psiElement, updateCtx);
        return updateCtx.getCurrentParameter();
    }

    private String getPresentation(Object[] itemsToShow, int paramIdx) {
        ParameterInfoUIContextEx uiCtx =
                ParameterInfoComponent.createContext(itemsToShow, myFixture.getEditor(), parameterInfoHandler, paramIdx);
        return parameterInfoHandler.getPresentation((CypherInvocation) itemsToShow[0], uiCtx);
    }

}
