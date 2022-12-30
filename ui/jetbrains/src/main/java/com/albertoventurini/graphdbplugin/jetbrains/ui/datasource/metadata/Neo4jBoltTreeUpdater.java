package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.*;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.neo4j.*;
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

import static com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.Neo4jTreeNodeType.*;

/**
 * Updates the data source tree for Neo4j data source metadata.
 */
final class Neo4jBoltTreeUpdater implements DataSourceTreeUpdater {

    private static final String RELATIONSHIP_TYPES_TITLE = "relationship types (%s)";
    private static final String PROPERTY_KEYS_TITLE = "property keys";
    private static final String LABELS_TITLE = "labels (%s)";
    private static final String PROCEDURES_TITLE = "procedures (%s)";
    private static final String FUNCTIONS_TITLE = "functions (%s)";
    private static final String INDEXES_TITLE = "indexes (%s)";
    private static final String CONSTRAINTS_TITLE = "constraints (%s)";

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
                new MetadataTreeNodeModel(
                        FUNCTIONS,
                        dataSourceApi,
                        FUNCTIONS_TITLE.formatted(functionMetadata.size()),
                        GraphIcons.Nodes.FUNCTION));

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
                new MetadataTreeNodeModel(
                        STORED_PROCEDURES,
                        dataSourceApi,
                        PROCEDURES_TITLE.formatted(procedureMetadata.size()),
                        GraphIcons.Nodes.STORED_PROCEDURE));

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

        dataSourceMetadata.getPropertyKeys().forEach(p ->
                propertyKeysTreeNode.add(of(new MetadataTreeNodeModel(PROPERTY_KEY, dataSourceApi, p))));

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
    private PatchedDefaultMutableTreeNode createLabelsNode(
            final Neo4jBoltCypherDataSourceMetadata dataSourceMetadata,
            final DataSourceApi dataSourceApi) {
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
        final List<Neo4jIndexMetadata> indexesMetadata = dataSourceMetadata.getIndexes();
        PatchedDefaultMutableTreeNode indexTreeNode = new PatchedDefaultMutableTreeNode(
                new MetadataTreeNodeModel(INDEXES,
                        dataSourceApi,
                        String.format(INDEXES_TITLE, indexesMetadata.size()),
                        GraphIcons.Nodes.INDEX));

        indexesMetadata
                .forEach(i -> indexTreeNode.add(of(new MetadataTreeNodeModel(INDEX, dataSourceApi,
                        i.name() + " " + i.state()))));

        return indexTreeNode;
    }

    private MutableTreeNode createConstraintsNode(DataSourceMetadata dataSourceMetadata, DataSourceApi dataSourceApi) {
        final List<Neo4jConstraintMetadata> constraintsMetadata = dataSourceMetadata.getConstraints();
        PatchedDefaultMutableTreeNode indexTreeNode = new PatchedDefaultMutableTreeNode(
                new MetadataTreeNodeModel(CONSTRAINTS, dataSourceApi,
                        String.format(CONSTRAINTS_TITLE, constraintsMetadata.size()), GraphIcons.Nodes.CONSTRAINT));
        constraintsMetadata
                .forEach(c ->
                        indexTreeNode.add(of(new MetadataTreeNodeModel(CONSTRAINT, dataSourceApi,
                                c.name()))));

        return indexTreeNode;
    }

    private PatchedDefaultMutableTreeNode of(MetadataTreeNodeModel model) {
        return new PatchedDefaultMutableTreeNode(model);
    }
}
