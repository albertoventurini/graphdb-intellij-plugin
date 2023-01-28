package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.parsing;

import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util.BaseParsingTest;

public class LexerTest extends BaseParsingTest {

    public LexerTest() {
        super("lexer");
    }

    public void testScientificNotation() {
        doTest(true);
    }

}
