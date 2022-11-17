/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.database.neo4j_4_4;

import com.albertoventurini.graphdbplugin.test.integration.neo4j.data.StoredProcedure;
import com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.database.common.AbstractDataSourceMetadataTest;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourceMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jBoltCypherDataSourceMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DataSourceMetadataTest extends AbstractDataSourceMetadataTest {

    @Override
    public DataSourceApi getDataSource() {
        return dataSource().neo4j40();
    }

    @Override
    protected List<StoredProcedure> requiredProcedures() {
        //TODO: needs update to match actual neo 4.0 procedures
        return Arrays.asList(
                procedure("db.labels",
                        "db.labels() :: (label :: STRING?)",
                        "List all available labels in the database.",
                        null,
                        null)
        );
    }

    public void testHaveTestUserFunctions() {
        DataSourceMetadata metadata = getMetadata();
        List<Map<String, String>> userFunctionsMetadata = metadata.getMetadata(Neo4jBoltCypherDataSourceMetadata.USER_FUNCTIONS);
        assertThat(userFunctionsMetadata)
                .isNotNull()
                .hasSize(145);
    }
}
