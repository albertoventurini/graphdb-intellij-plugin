/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourceType;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jBoltCypherDataSourceMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jLabelMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jRelationshipTypeMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.impl.DataSourceV1;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.*;
import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import static com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jBoltCypherDataSourceMetadata.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * Test for the {@link Neo4jBoltTreeUpdater} class.
 */
public class Neo4JBoltTreeUpdaterTest {

    private static final String UUID = "uuid";
    private static final String REL = "rel";
    private static final String PROPERTY = "prop";

    private static final List<Neo4jLabelMetadata> LABEL_METADATA = List.of(
            new Neo4jLabelMetadata("first label", 3L),
            new Neo4jLabelMetadata("second label", 100_000L));

    private PatchedDefaultMutableTreeNode root;
    private PatchedDefaultMutableTreeNode datasource;

    @Before
    public void setUp() {
        root = new PatchedDefaultMutableTreeNode(RootTreeNodeModel.ROOT_NAME);
        final var dataSourceApi = new DataSourceV1(UUID, "local", DataSourceType.NEO4J_BOLT, new HashMap<>());
        TreeNodeModelApi model = new DataSourceTreeNodeModel(dataSourceApi);
        datasource = new PatchedDefaultMutableTreeNode(model);

        root.add(datasource);
        Neo4jBoltCypherDataSourceMetadata metadata = new Neo4jBoltCypherDataSourceMetadata();

        HashMap<String, String> propertyKeys = new HashMap<>();
        propertyKeys.put("propertyKey", PROPERTY);

        HashMap<String, String> indexes = new HashMap<>();
        indexes.put("name", "DummyIndexName");
        indexes.put("state", "ONLINE");

        HashMap<String, String> constraints = new HashMap<>();
        constraints.put("description", "constraint ON (:aaa) UNIQUE");

        HashMap<String, String> procedures = new HashMap<>();
        procedures.put("signature", "db.labels() :: (label :: STRING?)");
        procedures.put("name", "db.labels");
        procedures.put("description", "List all labels in the database.");

        LABEL_METADATA.forEach(metadata::addLabel);
        metadata.addRelationshipType(new Neo4jRelationshipTypeMetadata(REL, 4L));
        metadata.addDataSourceMetadata(PROPERTY_KEYS, singletonList(propertyKeys));
        metadata.addDataSourceMetadata(STORED_PROCEDURES, singletonList(procedures));
        metadata.addDataSourceMetadata(USER_FUNCTIONS, singletonList(new HashMap<>()));
        metadata.addDataSourceMetadata(INDEXES, singletonList(indexes));
        metadata.addDataSourceMetadata(CONSTRAINTS, singletonList(constraints));

        var neo4jHandler = new Neo4jBoltTreeUpdater();
        neo4jHandler.updateTree(datasource, metadata);
    }

    @Test
    public void labelsArePopulatedAsExpected() {
        final var labelsNode = (DefaultMutableTreeNode) getChildByType(datasource, Neo4jTreeNodeType.LABELS);

        assertNotNull(labelsNode);
        assertNotNull(labelsNode.getUserObject());

        final var metadata = (MetadataTreeNodeModel) labelsNode.getUserObject();

        assertTrue(metadata.getText().isPresent());
        assertEquals("labels (%s)".formatted(LABEL_METADATA.size()), metadata.getText().get());

        assertEquals(LABEL_METADATA.size(), labelsNode.getChildCount());

        final var labelNodes = labelsNode.children();
        for (Neo4jLabelMetadata labelMetadata : LABEL_METADATA) {
            final var labelNode = (DefaultMutableTreeNode) labelNodes.nextElement();

            assertNotNull(labelNode);
            assertNotNull(labelNode.getUserObject());

            final var model = (LabelTreeNodeModel) labelNode.getUserObject();
            assertTrue(model.getText().isPresent());

            final String labelName = labelMetadata.getName();
            final int count = labelMetadata.getCount().intValue();
            assertEquals(labelName + " (" + count + ")", model.getText().get());
        }
    }

    @NotNull
    private Object[] getTreePath(Neo4jTreeNodeType group) {
        return new Object[]{root, datasource, getChildByType(datasource, group)};
    }

    @NotNull
    private Object[] getTreePath(Neo4jTreeNodeType child, Neo4jTreeNodeType subchild) {
        TreeNode node = getChildByType(datasource, child);
        TreeNode last = getChildByType(node, subchild);
        return new Object[]{root, datasource, node, last};
    }


    private TreeNode getChildByType(TreeNode node, Neo4jTreeNodeType type) {
        final Enumeration<? extends TreeNode> children = node.children();
        while (children.hasMoreElements()) {
            PatchedDefaultMutableTreeNode child = (PatchedDefaultMutableTreeNode) children.nextElement();
            TreeNodeModelApi model = ((TreeNodeModelApi) child.getUserObject());
            if (type == model.getType()) {
                return child;
            }
        }
        return null;
    }

}
