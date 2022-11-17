/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.database.common;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.test.integration.neo4j.data.StoredProcedure;
import com.albertoventurini.graphdbplugin.test.integration.neo4j.util.base.BaseIntegrationTest;



import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourceMetadata;

import static com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jBoltCypherDataSourceMetadata.STORED_PROCEDURES;
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
        Optional<DataSourceMetadata> metadata = component().dataSourcesMetadata().getMetadata(getDataSource()).get();
        assertThat(metadata).isPresent();
    }

    public void testMetadataHaveRequiredProcedures() {
        DataSourceMetadata metadata = getMetadata();
        List<Map<String, String>> storedProcedures = metadata.getMetadata(STORED_PROCEDURES);

        List<Map<String, String>> requiredProcedures = requiredProcedures().stream()
                .map(StoredProcedure::asMap)
                .collect(Collectors.toList());
        assertThat(storedProcedures)
                .containsAll(requiredProcedures);
    }

    protected abstract List<StoredProcedure> requiredProcedures();

    protected StoredProcedure procedure(final String name, final String signature, final String description, final String mode) {
        return new StoredProcedure(name, signature, description, mode);
    }

    protected StoredProcedure procedure(final String name, final String signature, final String description, final String mode, final String worksOnSystem) {
        return new StoredProcedure(name, signature, description, mode, worksOnSystem);
    }

    protected DataSourceMetadata getMetadata() {

        try {
            CompletableFuture<Optional<DataSourceMetadata>> futureMeta = component().dataSourcesMetadata().getMetadata(getDataSource());
            return futureMeta.get(30, TimeUnit.SECONDS)
                    .orElseThrow(() -> new IllegalStateException("Metadata should not be null"));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException("Failed to retrieve metadata", e);
        }
    }
}
