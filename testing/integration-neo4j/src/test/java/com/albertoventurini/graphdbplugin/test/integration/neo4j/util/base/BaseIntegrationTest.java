/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.util.base;

import com.albertoventurini.graphdbplugin.database.neo4j.bolt.Neo4jBoltConfiguration;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourceType;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourcesComponent;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourcesComponentMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.CypherMetadataContainer;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.CypherMetadataProviderService;
import com.albertoventurini.graphdbplugin.test.integration.neo4j.util.server.Neo4jServer;
import com.albertoventurini.graphdbplugin.test.integration.neo4j.util.server.Neo4jContainerServers;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseIntegrationTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String NEO4J52 = "neo4j52";
    private static final String UNAVAILABLE_DS = "unavailable";
    protected CypherMetadataContainer metadata;
    private Components components;
    private DataSources dataSources;
    private Services services;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        components = new Components();
        dataSources = new DataSources();
        services = new Services();

        // Clean up any data sources & their information
        List<DataSourceApi> dataSources = new ArrayList<>(component().dataSources().getDataSourceContainer().getDataSources());
        component().dataSources().getDataSourceContainer().removeDataSources(dataSources);
        services().cypherMetadataProvider().wipeAll();

        metadata = services().cypherMetadataProvider().getContainer("documentationTest");
    }

    public Services services() {
        return services;
    }

    public Components component() {
        return components;
    }

    public DataSources dataSource() {
        return dataSources;
    }

    private DataSourceApi createDataSource(
            final String name,
            final String host,
            final String port,
            final String user,
            final String password) {
        Map<String, String> configuration = new HashMap<>();
        configuration.put(Neo4jBoltConfiguration.HOST, host);
        configuration.put(Neo4jBoltConfiguration.PORT, port);
        configuration.put(Neo4jBoltConfiguration.USER, user);
        configuration.put(Neo4jBoltConfiguration.PASSWORD, password);

        return component().dataSources().getDataSourceContainer().createDataSource(
                null,
                DataSourceType.NEO4J_BOLT,
                name,
                configuration
        );
    }

    private DataSourceApi createDataSource(final String name, final Neo4jServer neo4jServer) {
        return createDataSource(name, neo4jServer.getBoltHost(), neo4jServer.getBoltPort(), null, null);
    }

    private DataSourceApi getNeo4jDataSource(final String dataSourceName, final Neo4jServer server) {
        return component().dataSources()
                .getDataSourceContainer()
                .getDataSource(dataSourceName)
                .orElseGet(() -> {
                    final DataSourceApi dataSource = createDataSource(dataSourceName, server);
                    component().dataSources().getDataSourceContainer().addDataSource(dataSource);
                    component().dataSources().refreshAllMetadata();
                    return dataSource;
                });
    }

    public final class Services {
        public CypherMetadataProviderService cypherMetadataProvider() {
            return getProject().getService(CypherMetadataProviderService.class);
        }
    }

    public final class Components {
        public DataSourcesComponent dataSources() {
            return getProject().getService(DataSourcesComponent.class);
        }

        public DataSourcesComponentMetadata dataSourcesMetadata() {
            return getProject().getService(DataSourcesComponentMetadata.class);
        }
    }

    /**
     * Lazily bootstrap Neo4j server and create data sources.
     * Some tests might even don't need running Neo4j server!
     */
    public final class DataSources {
        private DataSourceApi neo4j52DataSource;
        private DataSourceApi unavailableDataSource;

        public DataSourceApi neo4j52() {
            if (neo4j52DataSource == null) {
                neo4j52DataSource = getNeo4jDataSource(NEO4J52, Neo4jContainerServers.VERSION_5_2.getInstance());
            }
            return neo4j52DataSource;
        }

        public DataSourceApi unavailable() {
            if (unavailableDataSource == null) {
                unavailableDataSource = component().dataSources()
                        .getDataSourceContainer()
                        .getDataSource(UNAVAILABLE_DS)
                        .orElseGet(() -> {
                            DataSourceApi dataSource = createDataSource(UNAVAILABLE_DS, "unexisting.domain.dev",
                                    "7474", null, null);
                            component().dataSources().getDataSourceContainer().addDataSource(dataSource);
                            component().dataSources().refreshAllMetadata();
                            return dataSource;
                        });
            }
            return unavailableDataSource;
        }
    }
}
