/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.database.common;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourceMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.test.integration.neo4j.util.base.BaseIntegrationTest;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
public abstract class AbstractDataSourceMetadataTest extends BaseIntegrationTest {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        component().dataSources().refreshAllMetadata();
    }

    public abstract DataSourceApi getDataSource();

    public void testMetadataExists() throws ExecutionException, InterruptedException {
        Optional<DataSourceMetadata> metadata = component().dataSourcesMetadata().updateMetadata(getDataSource()).get();
        assertThat(metadata).isPresent();
    }

    protected DataSourceMetadata getMetadata() {
        try {
            CompletableFuture<Optional<DataSourceMetadata>> futureMeta = component().dataSourcesMetadata().updateMetadata(getDataSource());
            return futureMeta.get(30, TimeUnit.SECONDS)
                    .orElseThrow(() -> new IllegalStateException("Metadata should not be null"));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException("Failed to retrieve metadata", e);
        }
    }
}
