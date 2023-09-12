package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata;

import com.albertoventurini.graphdbplugin.database.api.data.GraphDatabaseVersion;
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

import static com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.Neo4jTreeNodeType.*;

/**
 * Updates the data source tree for Neo4j data source metadata.
 */
final class Neo4jBoltTreeUpdater implements DataSourceTreeUpdater<Neo4jMetadata> {

    private static final String VERSION_TITLE = "version: %s";
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
            final Neo4jMetadata neo4jMetadata) {

        // Remove existing metadata from ui
        dataSourceRootTreeNode.removeAllChildren();
        TreeNodeModelApi model = (TreeNodeModelApi) dataSourceRootTreeNode.getUserObject();
        DataSourceApi dataSourceApi = model.getDataSourceApi();

        dataSourceRootTreeNode.add(createVersionNode(neo4jMetadata.version(), dataSourceApi));
        dataSourceRootTreeNode.add(createConstraintsNode(neo4jMetadata.constraints(), dataSourceApi));
        dataSourceRootTreeNode.add(createIndexesNode(neo4jMetadata.indexes(), dataSourceApi));
        dataSourceRootTreeNode.add(createLabelsNode(neo4jMetadata, dataSourceApi));
        dataSourceRootTreeNode.add(createRelationshipTypesNode(neo4jMetadata, dataSourceApi));
        dataSourceRootTreeNode.add(createPropertyKeysNode(neo4jMetadata, dataSourceApi));

        if (!neo4jMetadata.procedures().isEmpty()) {
            dataSourceRootTreeNode.add(createProceduresNode(neo4jMetadata.procedures(), dataSourceApi));
        }

        if (!neo4jMetadata.functions().isEmpty()) {
            dataSourceRootTreeNode.add(createFunctionNode(neo4jMetadata.functions(), dataSourceApi));
        }
    }

    @NotNull
    private PatchedDefaultMutableTreeNode createVersionNode(
            final GraphDatabaseVersion version,
            final DataSourceApi dataSourceApi) {
        return of(new MetadataTreeNodeModel(
                VERSION,
                dataSourceApi,
                VERSION_TITLE.formatted(version.toString()),
                GraphIcons.Nodes.VERSION));
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
    private PatchedDefaultMutableTreeNode createPropertyKeysNode(Neo4jMetadata dataSourceMetadata, DataSourceApi dataSourceApi) {
        PatchedDefaultMutableTreeNode propertyKeysTreeNode = new PatchedDefaultMutableTreeNode(
                new MetadataTreeNodeModel(PROPERTY_KEYS, dataSourceApi, PROPERTY_KEYS_TITLE, GraphIcons.Nodes.PROPERTY_KEY));

        dataSourceMetadata.propertyKeys().forEach(p ->
                propertyKeysTreeNode.add(of(new MetadataTreeNodeModel(PROPERTY_KEY, dataSourceApi, p))));

        return propertyKeysTreeNode;
    }

    @NotNull
    private PatchedDefaultMutableTreeNode createRelationshipTypesNode(Neo4jMetadata dataSourceMetadata, DataSourceApi dataSourceApi) {
        int relationshipTypesCount = dataSourceMetadata.relationshipTypes().size();
        String relationshipTypesName = String.format(RELATIONSHIP_TYPES_TITLE, relationshipTypesCount);
        PatchedDefaultMutableTreeNode relationshipTypesTreeNode = new PatchedDefaultMutableTreeNode(
                new MetadataTreeNodeModel(RELATIONSHIPS, dataSourceApi, relationshipTypesName, GraphIcons.Nodes.RELATIONSHIP_TYPE));
        dataSourceMetadata.relationshipTypes()
                .stream()
                .map(rel -> new RelationshipTypeTreeNodeModel(RELATIONSHIP, dataSourceApi, rel.name(), rel.count()))
                .forEach(relModel -> relationshipTypesTreeNode.add(of(relModel)));
        return relationshipTypesTreeNode;
    }

    @NotNull
    private PatchedDefaultMutableTreeNode createLabelsNode(
            final Neo4jMetadata dataSourceMetadata,
            final DataSourceApi dataSourceApi) {
        int labelCount = dataSourceMetadata.labels().size();
        PatchedDefaultMutableTreeNode labelsTreeNode = new PatchedDefaultMutableTreeNode(
                new MetadataTreeNodeModel(LABELS, dataSourceApi, String.format(LABELS_TITLE, labelCount), GraphIcons.Nodes.LABEL));
        dataSourceMetadata.labels()
                .stream()
                .map(label -> new LabelTreeNodeModel(LABEL, dataSourceApi, label.name(), label.count()))
                .forEach(labelModel -> labelsTreeNode.add(of(labelModel)));
        return labelsTreeNode;
    }

    private MutableTreeNode createIndexesNode(
            final List<Neo4jIndexMetadata> indexesMetadata,
            final DataSourceApi dataSourceApi) {
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

    private MutableTreeNode createConstraintsNode(
            final List<Neo4jConstraintMetadata> constraintsMetadata,
            final DataSourceApi dataSourceApi) {
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
