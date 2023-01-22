/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.documentation;

import com.albertoventurini.graphdbplugin.language.cypher.documentation.database.CypherDocumentation;
import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util.BaseDocumentationTest;

public class BuiltInFunctionDocumentationTest extends BaseDocumentationTest {

    private String expectedDocumentation;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        expectedDocumentation = CypherDocumentation.BUILT_IN_FUNCTIONS.lookup("timestamp").get();
    }

    public void testFunctionBody() throws Exception {
        configure("RETURN timest<caret>amp();");
        verify(expectedDocumentation);
    }

    public void testFunctionParameters() throws Exception {
        configure("RETURN timestamp(<caret>);");
        verify(expectedDocumentation);
    }

    public void testIgnoreCase() throws Exception {
        configure("RETURN time<caret>STAMP();");
        verify(expectedDocumentation);
    }

    public void testNoDocumentation() {
        configure("RETURN nonexistingf<caret>unction();");
        verify(null);
    }
}
