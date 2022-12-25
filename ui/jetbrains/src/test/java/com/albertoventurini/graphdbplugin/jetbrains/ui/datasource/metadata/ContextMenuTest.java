/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourceType;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.dto.DataSourceContextMenu;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.dto.MetadataContextMenu;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.*;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jBoltCypherDataSourceMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jLabelMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jRelationshipTypeMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.impl.DataSourceV1;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.Enumeration;
import java.util.HashMap;

import static com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.Neo4jBoltCypherDataSourceMetadata.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

/**
 * Test that the correct context menu is associated with
 * each node in the metadata tree.
 */
@RunWith(JUnit4.class)
public class ContextMenuTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String UUID = "uuid";
    private static final String LABEL = "label";
    private static final String REL = "rel";
    private static final String PROPERTY = "prop";

    private PatchedDefaultMutableTreeNode root;
    private DataSourceV1 dataSourceApi;
    private ContextMenuService sut = new ContextMenuService();
    private PatchedDefaultMutableTreeNode datasource;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        root = new PatchedDefaultMutableTreeNode(RootTreeNodeModel.ROOT_NAME);
        dataSourceApi = new DataSourceV1(UUID, "local", DataSourceType.NEO4J_BOLT, new HashMap<>());
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

        metadata.addLabel(new Neo4jLabelMetadata(LABEL, 3L));
        metadata.addRelationshipType(new Neo4jRelationshipTypeMetadata(REL, 4L));
        metadata.addDataSourceMetadata(PROPERTY_KEYS, singletonList(propertyKeys));
        metadata.addDataSourceMetadata(STORED_PROCEDURES, singletonList(procedures));
        //metadata.addDataSourceMetadata(USER_FUNCTIONS, singletonList(new HashMap<>()));
        metadata.addDataSourceMetadata(INDEXES, singletonList(indexes));
        metadata.addDataSourceMetadata(CONSTRAINTS, singletonList(constraints));

        var neo4jHandler = new Neo4jBoltTreeUpdater();
        neo4jHandler.updateTree(datasource, metadata);
    }

    @Test
    public void personLabelClicked() {
        TreePath path = new TreePath(getTreePath(Neo4jTreeNodeType.LABELS, Neo4jTreeNodeType.LABEL));

        assertThat(sut.getContextMenu(path).get())
                .isEqualToComparingFieldByField(new MetadataContextMenu(Neo4jTreeNodeType.LABEL, dataSourceApi, LABEL));
    }

    @Test
    public void labelParentClicked() {
        TreePath path = new TreePath(getTreePath(Neo4jTreeNodeType.LABELS));

        assertThat(sut.getContextMenu(path)).isNotPresent();
    }

    @Test
    public void storedProcedureClicked() {
        TreePath path = new TreePath(getTreePath(Neo4jTreeNodeType.STORED_PROCEDURES, Neo4jTreeNodeType.STORED_PROCEDURE));

        assertThat(sut.getContextMenu(path)).isNotPresent();
    }

    @Test
    public void storedProcedureParentClicked() {
        TreePath path = new TreePath(getTreePath(Neo4jTreeNodeType.STORED_PROCEDURES));

        assertThat(sut.getContextMenu(path)).isNotPresent();
    }

    @Test
    public void relationshipClicked() {
        TreePath path = new TreePath(getTreePath(Neo4jTreeNodeType.RELATIONSHIPS, Neo4jTreeNodeType.RELATIONSHIP));

        assertThat(sut.getContextMenu(path).get())
                .isEqualToComparingFieldByField(new MetadataContextMenu(Neo4jTreeNodeType.RELATIONSHIP, dataSourceApi, REL));
    }

    @Test
    public void relationshipParentClicked() {
        TreePath path = new TreePath(getTreePath(Neo4jTreeNodeType.RELATIONSHIPS));

        assertThat(sut.getContextMenu(path)).isNotPresent();
    }

    @Test
    public void propertyClicked() {
        TreePath path = new TreePath(getTreePath(Neo4jTreeNodeType.PROPERTY_KEYS, Neo4jTreeNodeType.PROPERTY_KEY));

        assertThat(sut.getContextMenu(path).get())
                .isEqualToComparingFieldByField(new MetadataContextMenu(Neo4jTreeNodeType.PROPERTY_KEY, dataSourceApi, PROPERTY));
    }

    @Test
    public void propertyParentClicked() {
        TreePath path = new TreePath(getTreePath(Neo4jTreeNodeType.PROPERTY_KEYS));

        assertThat(sut.getContextMenu(path)).isNotPresent();
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

    @Test
    public void datasourceClicked() {
        Object[] pathObjects = new Object[]{
                root,
                datasource};
        TreePath path = new TreePath(pathObjects);

        assertThat(sut.getContextMenu(path).get())
                .isEqualToComparingFieldByField(new DataSourceContextMenu(dataSourceApi));
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

    @Test
    public void labelsArePopulatedAsExpected() {
        final var labelsNode = (DefaultMutableTreeNode) getChildByType(datasource, Neo4jTreeNodeType.LABELS);

        assertNotNull(labelsNode);
        assertNotNull(labelsNode.getUserObject());

        final var metadata = (MetadataTreeNodeModel) labelsNode.getUserObject();

        assertTrue(metadata.getText().isPresent());
        assertEquals("labels (1)", metadata.getText().get());

        assertEquals(1, labelsNode.getChildCount());

        final var labelNodes = labelsNode.children();
        final var labelNode = (DefaultMutableTreeNode) labelNodes.nextElement();

        assertNotNull(labelNode);
        assertNotNull(labelNode.getUserObject());

        final var model = (LabelTreeNodeModel) labelNode.getUserObject();
        assertTrue(model.getText().isPresent());

        assertEquals(LABEL + " (3)", model.getText().get());
    }
}
