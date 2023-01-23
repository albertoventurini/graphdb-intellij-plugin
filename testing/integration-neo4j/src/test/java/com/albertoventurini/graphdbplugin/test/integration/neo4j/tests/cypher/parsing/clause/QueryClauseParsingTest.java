/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.parsing.clause;

import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util.BaseParsingTest;

public class QueryClauseParsingTest extends BaseParsingTest {

    public QueryClauseParsingTest() {
        super("query/clause");
    }

    public void testCall() {
        doTest(true);
    }

    public void testCreate() {
        doTest(true);
    }

    public void testDelete() {
        doTest(true);
    }

    public void testForeach() {
        doTest(true);
    }

    public void testLoadCSV() {
        doTest(true);
    }

    public void testMatch() {
        doTest(true);
    }

    public void testMerge() {
        doTest(true);
    }

    public void testRemove() {
        doTest(true);
    }

    public void testReturn() {
        doTest(true);
    }

    public void testSet() {
        doTest(true);
    }

    public void testStart() {
        doTest(true);
    }

    public void testSubQueries() {
        doTest(true);
    }

    public void testSubQueriesInTransactions() {
        doTest(true);
    }

    public void testUnion() {
        doTest(true);
    }

    public void testUnwind() {
        doTest(true);
    }

    public void testWith() {
        doTest(true);
    }
}
