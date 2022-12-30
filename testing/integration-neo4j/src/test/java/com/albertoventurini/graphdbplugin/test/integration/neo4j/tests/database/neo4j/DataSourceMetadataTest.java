/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.database.neo4j;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourceMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j.Neo4jFunctionMetadata;
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
        DataSourceMetadata metadata = getMetadata();
        List<Neo4jFunctionMetadata> functionsMetadata = metadata.getFunctions();
        assertThat(functionsMetadata)
                .isNotEmpty();
    }
}
