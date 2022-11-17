/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util;

import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.CypherMetadataContainer;

public abstract class BaseCompletionTest extends BaseGenericTest {

    protected CypherMetadataContainer metadata;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        metadata = services().cypherMetadataProvider().getContainer("documentationTest");
    }

    public void addStoredProcedure(String name, String signature, String description) {
        metadata.addProcedure(name, signature, description);
    }

    public void addLabel(String name) {
        metadata.addLabel(name);
    }

    public void addRelationshipType(String name) {
        metadata.addRelationshipType(name);
    }

    public void addProperty(String name) {
        metadata.addPropertyKey(name);
    }
}
