/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.parsing;

import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util.BaseGenericTest;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import static com.albertoventurini.graphdbplugin.language.cypher.util.PsiTraversalUtilities.Cypher.getCypherStatementAtOffset;
import static org.assertj.core.api.Assertions.assertThat;

public class StatementTraversingTest extends BaseGenericTest {

    private static final String FAKE_CARET_SYMBOL = "|";

    public void testCypherStatementAtOffsetIfCaretNotSet() {
        PsiElement statement = cypherStatementAtOffset("MATCH (n) RETURN n;");
        assertThat(statement).isNull();
    }

    public void testCypherStatementAtOffsetWhereCaretInTheStartOfQuery() {
        PsiElement statement = cypherStatementAtOffset("%sMATCH (n) RETURN n;");
        assertThat(statement.getText()).isEqualTo("MATCH (n) RETURN n;");
    }

    public void testCypherStatementAtOffsetWhereCaretInTheEndOfQuery() {
        PsiElement statement = cypherStatementAtOffset("MATCH (n) RETURN n;%s");
        assertThat(statement.getText()).isEqualTo("MATCH (n) RETURN n;");
    }

    public void testCypherStatementAtOffsetWhereCaretBetweenMultipleQueries() {
        PsiElement statement = cypherStatementAtOffset("MATCH (n) RETURN n;%sRETURN 1;");
        assertThat(statement.getText()).isEqualTo("MATCH (n) RETURN n;");
    }

    public void testCypherStatementAtOffsetWhereCaretInTheEndOfFirstQueryAfterSpaceSymbol() {
        PsiElement statement = cypherStatementAtOffset("MATCH (n) RETURN n; %sRETURN 1;");
        assertThat(statement).isNull();
    }

    public void testCypherStatementAtOffsetWhereCaretInTheMiddleOfQuery() {
        PsiElement statement = cypherStatementAtOffset("MATCH (n) RETURN n; RETUR%sN 1;");
        assertThat(statement.getText()).isEqualTo("RETURN 1;");
    }

    public void testCypherStatementAtOffsetWhereCaretInTheEndOfLastQuery() {
        PsiElement statement = cypherStatementAtOffset("MATCH (n) RETURN n; RETURN 1;%s");
        assertThat(statement.getText()).isEqualTo("RETURN 1;");
    }

    public void testCypherStatementAtOffsetForTheMultilineQuery() {
        PsiElement statement = cypherStatementAtOffset("MATCH (n) \n%sRETURN n;");
        assertThat(statement.getText()).isEqualTo("MATCH (n) \nRETURN n;");
    }

    private PsiElement cypherStatementAtOffset(String queryTemplate) {
        String queryWithCaret = String.format(queryTemplate, FAKE_CARET_SYMBOL);
        String queryWithoutCaret = queryWithCaret.replace(FAKE_CARET_SYMBOL, "");
        int offset = queryWithCaret.indexOf(FAKE_CARET_SYMBOL);

        PsiFile psiFile = myFixture.configureByText("test.cyp", queryWithoutCaret);
        return getCypherStatementAtOffset(psiFile, offset);
    }

}
