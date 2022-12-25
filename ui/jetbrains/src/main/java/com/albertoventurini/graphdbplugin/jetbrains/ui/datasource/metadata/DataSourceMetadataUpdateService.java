/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourceType;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourcesComponentMetadata;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * This service retrieves the metadata associated with a {@link DataSourceApi}, then
 * updates the metadata tree by creating the appropriate elements (i.e. nodes and labels).
 * <br/>
 * The update logic is delegated to a {@link DataSourceTreeUpdater} that can handle the
 * provided {@link DataSourceType}.
 * <br/>
 * If none of the handlers can handle the data source type, then this service does nothing.
 */
@Service
public final class DataSourceMetadataUpdateService {

    static final String RELATIONSHIP_TYPES_TITLE = "relationship types (%s)";
    static final String PROPERTY_KEYS_TITLE = "property keys";
    static final String LABELS_TITLE = "labels (%s)";
    static final String STORED_PROCEDURES_TITLE = "stored procedures";
    static final String FUNCTIONS_TITLE = "user functions";
    static final String INDEXES_TITLE = "indexes (%s)";
    static final String CONSTRAINTS_TITLE = "constraints (%s)";

    private final DataSourcesComponentMetadata dataSourcesComponent;

    private final DataSourceTreeUpdaters treeUpdaters;

    DataSourceMetadataUpdateService(@NotNull final Project project) {
        dataSourcesComponent = project.getService(DataSourcesComponentMetadata.class);
        treeUpdaters = project.getService(DataSourceTreeUpdaters.class);
    }

    /**
     * Retrieve metadata and update the UI.
     * @param node the root node of the tree that displays the metadata
     * @param nodeDataSource a {@link DataSourceApi} which describes the data source
     * @return a {@link CompletableFuture<Boolean>} which will complete to true if everything went well,
     * or false if the metadata update could not happen.
     */
    public CompletableFuture<Boolean> updateDataSourceMetadataUi(
            final PatchedDefaultMutableTreeNode node,
            final DataSourceApi nodeDataSource) {
        final DataSourceType sourceType = nodeDataSource.getDataSourceType();

        return treeUpdaters.get(sourceType)
                .map(updater -> getMetadataAndApplyHandler(updater, node, nodeDataSource))
                .orElse(completedFuture(false));
    }

    private CompletableFuture<Boolean> getMetadataAndApplyHandler(
            final DataSourceTreeUpdater updater,
            final PatchedDefaultMutableTreeNode node,
            final DataSourceApi nodeDataSource) {
        return dataSourcesComponent.getMetadata(nodeDataSource)
                .thenApply(data -> data.map(d -> {
                        updater.updateTree(node, d);
                        return true;
                    }).orElse(false)
                );
    }
}
