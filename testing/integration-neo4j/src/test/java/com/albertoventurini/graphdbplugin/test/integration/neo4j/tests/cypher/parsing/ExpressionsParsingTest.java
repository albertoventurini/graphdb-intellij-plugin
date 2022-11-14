/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.parsing;

import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util.BaseParsingTest;

public class ExpressionsParsingTest extends BaseParsingTest {

    public ExpressionsParsingTest() {
        super("expressions");
    }

    public void testExpressions() {
        doTest(true);
    }

    public void testUserFunction() {
        doTest(true);
    }

    public void testParameters() {
        doTest(true);
    }

    public void testKeywordInIdentifier() {
        doTest(true);
    }

    public void testSpecialFunctions() {
        doTest(true);
    }

    public void testMapProjection() {
        doTest(true);
    }

    public void testPatternComprehension() {
        doTest(true);
    }

    public void testCountFunction() {
        doTest(true);
    }
}
