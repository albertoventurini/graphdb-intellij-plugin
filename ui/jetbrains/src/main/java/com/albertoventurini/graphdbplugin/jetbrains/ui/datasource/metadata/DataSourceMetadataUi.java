/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourceType;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourceMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourcesComponentMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jBoltCypherDataSourceMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.LabelTreeNodeModel;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.MetadataTreeNodeModel;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.RelationshipTypeTreeNodeModel;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.TreeNodeModelApi;
import icons.GraphIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.MutableTreeNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

import static com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.Neo4jTreeNodeType.*;
import static java.util.concurrent.CompletableFuture.completedFuture;

public class DataSourceMetadataUi {

    private static final String RELATIONSHIP_TYPES_TITLE = "relationship types (%s)";
    private static final String PROPERTY_KEYS_TITLE = "property keys";
    private static final String LABELS_TITLE = "labels (%s)";
    private static final String STORED_PROCEDURES_TITLE = "stored procedures";
    private static final String USER_FUNCTIONS_TITLE = "user functions";
    private static final String INDEXES_TITLE = "indexes (%s)";
    private static final String CONSTRAINTS_TITLE = "constraints (%s)";

    private final DataSourcesComponentMetadata dataSourcesComponent;

    private final Map<DataSourceType, BiFunction<PatchedDefaultMutableTreeNode, Neo4jBoltCypherDataSourceMetadata, Boolean>> handlers = new HashMap<>();

    public DataSourceMetadataUi(DataSourcesComponentMetadata dataSourcesComponent) {
        this.dataSourcesComponent = dataSourcesComponent;

        handlers.put(DataSourceType.NEO4J_BOLT, this::updateNeo4jBoltCypherMetadataUi);
    }

    public CompletableFuture<Boolean> updateDataSourceMetadataUi(PatchedDefaultMutableTreeNode node, DataSourceApi nodeDataSource) {
        DataSourceType sourceType = nodeDataSource.getDataSourceType();
        if (handlers.containsKey(sourceType)) {
            return dataSourcesComponent.getMetadata(nodeDataSource)
                    .thenApply((data) ->
                            data.map(genericMetadata -> (Neo4jBoltCypherDataSourceMetadata) genericMetadata)
                                    .map(neo4jMetadata -> handlers.get(sourceType).apply(node, neo4jMetadata))
                                    .orElse(false));
        } else {
            return completedFuture(false);
        }
    }

    // ui
    boolean updateNeo4jBoltCypherMetadataUi(PatchedDefaultMutableTreeNode dataSourceRootTreeNode,
                                            Neo4jBoltCypherDataSourceMetadata dataSourceMetadata) {
        // Remove existing metadata from ui
        dataSourceRootTreeNode.removeAllChildren();
        TreeNodeModelApi model = (TreeNodeModelApi) dataSourceRootTreeNode.getUserObject();
        DataSourceApi dataSourceApi = model.getDataSourceApi();

        dataSourceRootTreeNode.add(createConstraintsNode(dataSourceMetadata, dataSourceApi));
        dataSourceRootTreeNode.add(createIndexesNode(dataSourceMetadata, dataSourceApi));
        dataSourceRootTreeNode.add(createLabelsNode(dataSourceMetadata, dataSourceApi));
        dataSourceRootTreeNode.add(createRelationshipTypesNode(dataSourceMetadata, dataSourceApi));
        dataSourceRootTreeNode.add(createPropertyKeysNode(dataSourceMetadata, dataSourceApi));
        dataSourceRootTreeNode.add(createStoredProceduresNode(dataSourceMetadata, dataSourceApi));

        if (dataSourceMetadata.isMetadataExists(Neo4jBoltCypherDataSourceMetadata.USER_FUNCTIONS)) {
            dataSourceRootTreeNode.add(createUserFunctionNode(dataSourceMetadata, dataSourceApi));
        }

        return true;
    }

    @NotNull
    private PatchedDefaultMutableTreeNode createUserFunctionNode(Neo4jBoltCypherDataSourceMetadata dataSourceMetadata, DataSourceApi dataSourceApi) {
        PatchedDefaultMutableTreeNode userFunctionTreeNode = new PatchedDefaultMutableTreeNode(
            new MetadataTreeNodeModel(USER_FUNCTIONS, dataSourceApi, USER_FUNCTIONS_TITLE, GraphIcons.Nodes.USER_FUNCTION));

        dataSourceMetadata
            .getMetadata(Neo4jBoltCypherDataSourceMetadata.USER_FUNCTIONS)
            .forEach((row) -> {
                PatchedDefaultMutableTreeNode nameNode = of(new MetadataTreeNodeModel(USER_FUNCTION, dataSourceApi, row.get("name")));
                PatchedDefaultMutableTreeNode descriptionNode = of(new MetadataTreeNodeModel(USER_FUNCTION, dataSourceApi, row.get("signature")));
                nameNode.add(descriptionNode);
                userFunctionTreeNode.add(nameNode);
            });
        return userFunctionTreeNode;
    }

    @NotNull
    private PatchedDefaultMutableTreeNode createStoredProceduresNode(Neo4jBoltCypherDataSourceMetadata dataSourceMetadata, DataSourceApi dataSourceApi) {
        PatchedDefaultMutableTreeNode storedProceduresTreeNode = new PatchedDefaultMutableTreeNode(
            new MetadataTreeNodeModel(STORED_PROCEDURES, dataSourceApi, STORED_PROCEDURES_TITLE, GraphIcons.Nodes.STORED_PROCEDURE));
        dataSourceMetadata
            .getMetadata(Neo4jBoltCypherDataSourceMetadata.STORED_PROCEDURES)
            .forEach((row) -> {
                PatchedDefaultMutableTreeNode nameNode = of(new MetadataTreeNodeModel(STORED_PROCEDURE, dataSourceApi, row.get("name")));
                PatchedDefaultMutableTreeNode descriptionNode = of(new MetadataTreeNodeModel(STORED_PROCEDURE, dataSourceApi, row.get("signature")));
                nameNode.add(descriptionNode);
                storedProceduresTreeNode.add(nameNode);
            });
        return storedProceduresTreeNode;
    }

    @NotNull
    private PatchedDefaultMutableTreeNode createPropertyKeysNode(Neo4jBoltCypherDataSourceMetadata dataSourceMetadata, DataSourceApi dataSourceApi) {
        PatchedDefaultMutableTreeNode propertyKeysTreeNode = new PatchedDefaultMutableTreeNode(
            new MetadataTreeNodeModel(PROPERTY_KEYS, dataSourceApi, PROPERTY_KEYS_TITLE, GraphIcons.Nodes.PROPERTY_KEY));
        dataSourceMetadata
            .getMetadata(Neo4jBoltCypherDataSourceMetadata.PROPERTY_KEYS)
            .forEach((row) -> propertyKeysTreeNode.add(of(new MetadataTreeNodeModel(PROPERTY_KEY, dataSourceApi, row.get("propertyKey")))));
        return propertyKeysTreeNode;
    }

    @NotNull
    private PatchedDefaultMutableTreeNode createRelationshipTypesNode(Neo4jBoltCypherDataSourceMetadata dataSourceMetadata, DataSourceApi dataSourceApi) {
        int relationshipTypesCount = dataSourceMetadata.getRelationshipTypes().size();
        String relationshipTypesName = String.format(RELATIONSHIP_TYPES_TITLE, relationshipTypesCount);
        PatchedDefaultMutableTreeNode relationshipTypesTreeNode = new PatchedDefaultMutableTreeNode(
            new MetadataTreeNodeModel(RELATIONSHIPS, dataSourceApi, relationshipTypesName, GraphIcons.Nodes.RELATIONSHIP_TYPE));
        dataSourceMetadata.getRelationshipTypes()
            .stream()
            .map(rel -> new RelationshipTypeTreeNodeModel(RELATIONSHIP, dataSourceApi, rel.getName(), rel.getCount()))
            .forEach(relModel -> relationshipTypesTreeNode.add(of(relModel)));
        return relationshipTypesTreeNode;
    }

    @NotNull
    private PatchedDefaultMutableTreeNode createLabelsNode(Neo4jBoltCypherDataSourceMetadata dataSourceMetadata, DataSourceApi dataSourceApi) {
        int labelCount = dataSourceMetadata.getLabels().size();
        PatchedDefaultMutableTreeNode labelsTreeNode = new PatchedDefaultMutableTreeNode(
            new MetadataTreeNodeModel(LABELS, dataSourceApi, String.format(LABELS_TITLE, labelCount), GraphIcons.Nodes.LABEL));
        dataSourceMetadata.getLabels()
            .stream()
            .map(label -> new LabelTreeNodeModel(LABEL, dataSourceApi, label.getName(), label.getCount()))
            .forEach(labelModel -> labelsTreeNode.add(of(labelModel)));
        return labelsTreeNode;
    }

    private MutableTreeNode createIndexesNode(DataSourceMetadata dataSourceMetadata, DataSourceApi dataSourceApi) {
        List<Map<String, String>> indexesMetadata =
                dataSourceMetadata.getMetadata(Neo4jBoltCypherDataSourceMetadata.INDEXES);
        PatchedDefaultMutableTreeNode indexTreeNode = new PatchedDefaultMutableTreeNode(
                new MetadataTreeNodeModel(INDEXES,
                        dataSourceApi,
                        String.format(INDEXES_TITLE, indexesMetadata.size()),
                        GraphIcons.Nodes.INDEX));
        indexesMetadata
                .forEach(row -> indexTreeNode.add(of(new MetadataTreeNodeModel(INDEX, dataSourceApi,
                        row.get("name") + " " + row.get("state")))));

        return indexTreeNode;
    }

    private MutableTreeNode createConstraintsNode(DataSourceMetadata dataSourceMetadata, DataSourceApi dataSourceApi) {
        List<Map<String, String>> constraintsMetadata =
                dataSourceMetadata.getMetadata(Neo4jBoltCypherDataSourceMetadata.CONSTRAINTS);
        PatchedDefaultMutableTreeNode indexTreeNode = new PatchedDefaultMutableTreeNode(
                new MetadataTreeNodeModel(CONSTRAINTS, dataSourceApi,
                        String.format(CONSTRAINTS_TITLE, constraintsMetadata.size()), GraphIcons.Nodes.CONSTRAINT));
        constraintsMetadata
                .forEach(row ->
                        indexTreeNode.add(of(new MetadataTreeNodeModel(CONSTRAINT, dataSourceApi,
                                row.get("description").substring(11)))));

        return indexTreeNode;
    }

    private PatchedDefaultMutableTreeNode of(MetadataTreeNodeModel model) {
        return new PatchedDefaultMutableTreeNode(model);
    }
}
