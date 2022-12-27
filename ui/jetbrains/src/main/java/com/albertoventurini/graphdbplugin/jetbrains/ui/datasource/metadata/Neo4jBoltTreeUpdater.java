package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourceMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jBoltCypherDataSourceMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jFunctionMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jProcedureMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.LabelTreeNodeModel;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.MetadataTreeNodeModel;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.RelationshipTypeTreeNodeModel;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.TreeNodeModelApi;
import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import icons.GraphIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.MutableTreeNode;
import java.util.List;
import java.util.Map;

import static com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.DataSourceMetadataUpdateService.*;
import static com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.Neo4jTreeNodeType.*;
import static com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.Neo4jTreeNodeType.CONSTRAINT;

/**
 * Updates the data source tree for Neo4j data source metadata.
 */
final class Neo4jBoltTreeUpdater implements DataSourceTreeUpdater {

    @Override
    public void updateTree(
            final PatchedDefaultMutableTreeNode dataSourceRootTreeNode,
            final DataSourceMetadata metadata) {

        final var neo4jMetadata = (Neo4jBoltCypherDataSourceMetadata) metadata;

        // Remove existing metadata from ui
        dataSourceRootTreeNode.removeAllChildren();
        TreeNodeModelApi model = (TreeNodeModelApi) dataSourceRootTreeNode.getUserObject();
        DataSourceApi dataSourceApi = model.getDataSourceApi();

        dataSourceRootTreeNode.add(createConstraintsNode(neo4jMetadata, dataSourceApi));
        dataSourceRootTreeNode.add(createIndexesNode(neo4jMetadata, dataSourceApi));
        dataSourceRootTreeNode.add(createLabelsNode(neo4jMetadata, dataSourceApi));
        dataSourceRootTreeNode.add(createRelationshipTypesNode(neo4jMetadata, dataSourceApi));
        dataSourceRootTreeNode.add(createPropertyKeysNode(neo4jMetadata, dataSourceApi));

        if (!metadata.getProcedures().isEmpty()) {
            dataSourceRootTreeNode.add(createProceduresNode(neo4jMetadata.getProcedures(), dataSourceApi));
        }

        if (!metadata.getFunctions().isEmpty()) {
            dataSourceRootTreeNode.add(createFunctionNode(neo4jMetadata.getFunctions(), dataSourceApi));
        }
    }

    @NotNull
    private PatchedDefaultMutableTreeNode createFunctionNode(
            final List<Neo4jFunctionMetadata> functionMetadata,
            final DataSourceApi dataSourceApi) {
        final PatchedDefaultMutableTreeNode functionTreeNode = new PatchedDefaultMutableTreeNode(
                new MetadataTreeNodeModel(FUNCTIONS, dataSourceApi, FUNCTIONS_TITLE, GraphIcons.Nodes.FUNCTION));

        functionMetadata.forEach(f -> {
            final PatchedDefaultMutableTreeNode nameNode =
                    of(new MetadataTreeNodeModel(FUNCTION, dataSourceApi, f.name()));
            final PatchedDefaultMutableTreeNode descriptionNode =
                    of(new MetadataTreeNodeModel(FUNCTION, dataSourceApi, f.description()));
            nameNode.add(descriptionNode);
            functionTreeNode.add(nameNode);
        });

        return functionTreeNode;
    }

    @NotNull
    private PatchedDefaultMutableTreeNode createProceduresNode(
            final List<Neo4jProcedureMetadata> procedureMetadata,
            final DataSourceApi dataSourceApi) {
        PatchedDefaultMutableTreeNode storedProceduresTreeNode = new PatchedDefaultMutableTreeNode(
                new MetadataTreeNodeModel(STORED_PROCEDURES, dataSourceApi, STORED_PROCEDURES_TITLE, GraphIcons.Nodes.STORED_PROCEDURE));

        procedureMetadata
                .forEach(p -> {
                    final PatchedDefaultMutableTreeNode nameNode =
                            of(new MetadataTreeNodeModel(STORED_PROCEDURE, dataSourceApi,p.name()));
                    final PatchedDefaultMutableTreeNode descriptionNode =
                            of(new MetadataTreeNodeModel(STORED_PROCEDURE, dataSourceApi, p.description()));
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
                                row.get("name")))));

        return indexTreeNode;
    }

    private PatchedDefaultMutableTreeNode of(MetadataTreeNodeModel model) {
        return new PatchedDefaultMutableTreeNode(model);
    }
}
