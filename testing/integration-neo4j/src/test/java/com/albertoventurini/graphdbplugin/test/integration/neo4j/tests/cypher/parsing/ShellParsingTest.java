/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.parsing;

import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util.BaseParsingTest;

public class ShellParsingTest extends BaseParsingTest {

    public ShellParsingTest() {
        super("shell");
    }

    public void testKeywords() {
        doTest(true);
    }
}
