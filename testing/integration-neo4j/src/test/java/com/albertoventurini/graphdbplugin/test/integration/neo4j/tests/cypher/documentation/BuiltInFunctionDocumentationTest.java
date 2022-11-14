/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.documentation;

import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util.BaseDocumentationTest;


public class BuiltInFunctionDocumentationTest extends BaseDocumentationTest {

    private String expectedDocumentation;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        expectedDocumentation = CypherDocumentation.BUILT_IN_FUNCTIONS.lookup("toString").get();
    }

    public void testFunctionBody() throws Exception {
        configure("RETURN toStr<caret>ing(42);");
        verify(expectedDocumentation);
    }

    public void testFunctionParameters() throws Exception {
        configure("RETURN toString(4<caret>2);");
        verify(expectedDocumentation);
    }

    public void testIgnoreCase() throws Exception {
        configure("RETURN to<caret>STRING(42);");
        verify(expectedDocumentation);
    }

    public void testNoDocumentation() {
        configure("RETURN nonexistingf<caret>unction();");
        verify(null);
    }
}
