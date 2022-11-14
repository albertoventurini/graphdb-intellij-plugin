/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.reference;

import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util.BaseCompletionTest;

public class RelationshipTypeReferenceContributorTest extends BaseCompletionTest {

    public void testPresentInCompletion() throws Exception {
        myFixture.configureByText("test.cyp", "MATCH ()-[:TESTRELTYPE]-() MATCH ()-[:TESTREL<caret>");
        myFixture.completeBasic();
        myFixture.checkResult("MATCH ()-[:TESTRELTYPE]-() MATCH ()-[:TESTRELTYPE<caret>");
    }

    public void _testOneEntryPresentInCompletionIfMetadataExists() throws Exception {
        addRelationshipType("TESTRELTYPE");
        myFixture.configureByText("test.cyp", "MATCH ()-[:TESTRELTYPE]-() MATCH ()-[:TESTREL<caret>");
        myFixture.completeBasic();
        myFixture.checkResult("MATCH ()-[:TESTRELTYPE]-() MATCH ()-[:TESTRELTYPE<caret>");
    }
}
