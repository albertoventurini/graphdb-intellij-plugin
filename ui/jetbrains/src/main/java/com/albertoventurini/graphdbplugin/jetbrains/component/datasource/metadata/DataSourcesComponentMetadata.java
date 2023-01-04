/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourceType;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j.Neo4jMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j.Neo4jLabelMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j.Neo4jMetadataBuilder;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j.Neo4jRelationshipTypeMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.services.ExecutorService;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.MetadataRetrieveEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.CypherMetadataContainer;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.CypherMetadataProviderService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DataSourcesComponentMetadata {

    private final Map<DataSourceType, MetadataBuilder> handlers = new HashMap<>();
    private CypherMetadataProviderService cypherMetadataProviderService;
    private ExecutorService executorService;
    private MessageBus messageBus;

    public DataSourcesComponentMetadata(final Project project) {
        messageBus = project.getMessageBus();
        cypherMetadataProviderService = project.getService(CypherMetadataProviderService.class);
        executorService = ApplicationManager.getApplication().getService(ExecutorService.class);

        handlers.put(DataSourceType.NEO4J_BOLT, new Neo4jMetadataBuilder());
    }

    public CompletableFuture<Optional<DataSourceMetadata>> getMetadata(DataSourceApi dataSource) {
        MetadataRetrieveEvent metadataRetrieveEvent = messageBus.syncPublisher(MetadataRetrieveEvent.METADATA_RETRIEVE_EVENT);

        metadataRetrieveEvent.startMetadataRefresh(dataSource);

        CompletableFuture<Optional<DataSourceMetadata>> future = new CompletableFuture<>();
        DataSourceType sourceType = dataSource.getDataSourceType();
        if (handlers.containsKey(sourceType)) {
            executorService.runInBackground(
                    () -> handlers.get(sourceType).buildMetadata(dataSource),
                    (metadata) -> {
                        updateNeo4jBoltMetadata(dataSource, (Neo4jMetadata) metadata);
                        metadataRetrieveEvent.metadataRefreshSucceed(dataSource);
                        future.complete(Optional.of(metadata));
                    },
                    (exception) -> {
                        metadataRetrieveEvent.metadataRefreshFailed(dataSource, exception);
                        future.complete(Optional.empty());
                    }
            );
        } else {
            metadataRetrieveEvent.metadataRefreshFailed(dataSource, new RuntimeException("Metadata are not supported"));
            future.complete(Optional.empty());
        }
        return future;
    }

    private void updateNeo4jBoltMetadata(DataSourceApi dataSource, Neo4jMetadata metadata) {
        // Refresh cypher metadata provider
        cypherMetadataProviderService.wipeContainer(dataSource.getName());
        CypherMetadataContainer container = cypherMetadataProviderService.getContainer(dataSource.getName());

        metadata.labels()
                .stream()
                .map(Neo4jLabelMetadata::name)
                .forEach(container::addLabel);
        metadata.relationshipTypes()
                .stream()
                .map(Neo4jRelationshipTypeMetadata::name)
                .forEach(container::addRelationshipType);

        metadata.propertyKeys().forEach(container::addPropertyKey);
        metadata.procedures().forEach(p -> container.addProcedure(p.name(), p.signature(), p.description()));
        metadata.functions().forEach(f -> container.addFunction(f.name(), f.signature(), f.description()));
    }
}
