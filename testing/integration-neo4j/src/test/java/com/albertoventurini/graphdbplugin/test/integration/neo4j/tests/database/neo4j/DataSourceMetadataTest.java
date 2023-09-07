/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.database.neo4j;

import com.albertoventurini.graphdbplugin.database.neo4j.bolt.data.Neo4jGraphDatabaseVersion;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j.Neo4jMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j.Neo4jFunctionMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j.Neo4jProcedureMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.database.common.AbstractDataSourceMetadataTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DataSourceMetadataTest extends AbstractDataSourceMetadataTest {

    @Override
    public DataSourceApi getDataSource() {
        return dataSource().neo4j52();
    }

    public void testHaveTestUserFunctions() {
        final Neo4jMetadata metadata = (Neo4jMetadata) getMetadata();
        final List<Neo4jFunctionMetadata> functionsMetadata = metadata.functions();
        assertThat(functionsMetadata)
                .isNotEmpty();
    }

    public void testMetadataHaveRequiredProcedures() {
        final Neo4jMetadata metadata = (Neo4jMetadata) getMetadata();
        final List<Neo4jProcedureMetadata> procedures = metadata.procedures();

        assertTrue(procedures.stream().anyMatch(p ->
                p.name().equals("db.labels")
                        && p.signature().equals("db.labels() :: (label :: STRING?)")
                        && p.description().equals("List all available labels in the database.")));
    }

    public void testGetVersion() {
        var metadata = (Neo4jMetadata) getMetadata();
        var version = (Neo4jGraphDatabaseVersion) metadata.version();
        assertEquals(version.major(), 5);
        assertEquals(version.minor(), 2);
    }
}
