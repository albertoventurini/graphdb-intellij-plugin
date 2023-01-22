/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.documentation;

import com.albertoventurini.graphdbplugin.language.cypher.documentation.database.CypherDocumentation;
import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util.BaseDocumentationTest;


public class BuiltInSpecialFunctionDocumentationTest extends BaseDocumentationTest {

    private String shortestPathDocumentation;
    private String allShortestPathsDocumentation;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        shortestPathDocumentation = CypherDocumentation.BUILT_IN_FUNCTIONS.lookup("shortestpath").get();
        allShortestPathsDocumentation = CypherDocumentation.BUILT_IN_FUNCTIONS.lookup("allshortestpaths").get();
    }

    public void testShortestPath() throws Exception {
        configure("RETURN shortest<caret>Path((n));");
        verify(shortestPathDocumentation);
    }

    public void testShortestPathParameters() throws Exception {
        configure("RETURN shortestPath((n<caret>));");
        verify(shortestPathDocumentation);
    }

    public void testAllShortestPaths() throws Exception {
        configure("RETURN allShortest<caret>Paths((n));");
        verify(allShortestPathsDocumentation);
    }

    public void testAllShortestPathsParameters() throws Exception {
        configure("RETURN allShortestPaths((n<caret>));");
        verify(allShortestPathsDocumentation);
    }
}
