package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.parsing;

import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util.BaseParsingTest;

public class IndexCommandParsingTest extends BaseParsingTest {

    public IndexCommandParsingTest() {
        super("indexes");
    }

    public void testCreateNamedIndex() {
        doTest(true);
    }

    public void testCreateUnnamedIndex() {
        doTest(true);
    }

    public void testCreateIndexOnRelationships() {
        doTest(true);
    }

    public void testCreateIndexWithOptions() {
        doTest(true);
    }

    public void testCreateIndexIfNotExists() {
        doTest(true);
    }

    public void testCreateRangeIndex() {
        doTest(true);
    }

    public void testCreateLookupIndex() {
        doTest(true);
    }

    public void testCreatePointIndex() {
        doTest(true);
    }

    public void testCreateTextIndex() {
        doTest(true);
    }

    public void testDropIndex() {
        doTest(true);
    }
}
