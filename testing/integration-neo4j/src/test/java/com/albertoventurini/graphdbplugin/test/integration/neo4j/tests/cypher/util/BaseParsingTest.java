/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util;

import com.google.common.io.Resources;
import com.intellij.testFramework.ParsingTestCase;


/**
 * Base for all parsing test cases.
 */
public abstract class BaseParsingTest  extends ParsingTestCase {

    public BaseParsingTest(String testDataFolder) {
        super(testDataFolder, "cyp", new CypherParserDefinition());
    }

    @Override
    protected String getTestDataPath() {
        return Resources.getResource("parsing").getFile();
    }

    @Override
    protected boolean skipSpaces() {
        return true;
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }
}
