/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util;

public abstract class BaseRenameTest extends BaseCodeInsightTest {

    public BaseRenameTest(String dataPath) {
        super("rename", dataPath);
    }

    protected void verify(String newName) {
        myFixture.configureByFiles(getTestName() + ".cyp");
        myFixture.renameElementAtCaret(newName);
        myFixture.checkResultByFile(getTestName() + "After.cyp");
    }
}
