/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.rename;


import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util.BaseRenameTest;

public class LabelRenameTest extends BaseRenameTest {

    public LabelRenameTest() {
        super("label");
    }

    public void testSingleQuery() {
        verify("RENAMED_LABEL");
    }

    public void testMultipleQueries() {
        verify("RENAMED_LABEL");
    }
}
