/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourcesComponent;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.actions.RefreshDataSourcesAction;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.interactions.DataSourceInteractions;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.DataSourceMetadataUpdateService;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.*;
import com.albertoventurini.graphdbplugin.jetbrains.util.FileUtil;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.ActionToolbarPosition;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.intellij.ui.treeStructure.Tree;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourcesComponentMetadata;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class DataSourcesView implements Disposable {

    private static final Logger LOG = Logger.getInstance(DataSourcesView.class);

    private boolean initialized;

    private DataSourcesComponent component;
    private DataSourcesComponentMetadata componentMetadata;
    private DataSourceInteractions interactions;
    private PatchedDefaultMutableTreeNode treeRoot;
    private DefaultTreeModel treeModel;

    private JPanel toolWindowContent;
    private JPanel treePanel;
    private Tree dataSourceTree;
    private ToolbarDecorator decorator;
    private DataSourceMetadataUpdateService dataSourceMetadataUpdateService;

    public DataSourcesView() {
        initialized = false;
    }

    public void initToolWindow(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        if (!initialized) {
            ContentFactory contentFactory = ContentFactory.getInstance();
            Content content = contentFactory.createContent(toolWindowContent, "", false);
            toolWindow.getContentManager().addContent(content);

            component = project.getService(DataSourcesComponent.class);
            componentMetadata = project.getService(DataSourcesComponentMetadata.class);
            dataSourceMetadataUpdateService = project.getService(DataSourceMetadataUpdateService.class); //  new DataSourceMetadataUi(componentMetadata);
            treeRoot = new PatchedDefaultMutableTreeNode(new RootTreeNodeModel());
            treeModel = new DefaultTreeModel(treeRoot, false);
            decorator = ToolbarDecorator.createDecorator(dataSourceTree);
            decorator.addExtraAction(new RefreshDataSourcesAction(this));

            configureDataSourceTree();
            decorateDataSourceTree();

            interactions = new DataSourceInteractions(project, this);

            replaceTreeWithDecorated();
            showDataSources();
            refreshDataSourcesMetadata();

            initialized = true;
        }
    }

    public DataSourcesComponent getComponent() {
        return component;
    }

    public Tree getDataSourceTree() {
        return dataSourceTree;
    }

    public PatchedDefaultMutableTreeNode getTreeRoot() {
        return treeRoot;
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    public ToolbarDecorator getDecorator() {
        return decorator;
    }

    private void createUIComponents() {
        treePanel = new JPanel(new GridLayout(0, 1));
    }

    private void configureDataSourceTree() {
        dataSourceTree.getEmptyText().setText("Create a data source");
        dataSourceTree.setCellRenderer(new GraphColoredTreeCellRenderer(component));
        dataSourceTree.setModel(treeModel);
        dataSourceTree.setRootVisible(false);
        dataSourceTree.setToggleClickCount(0);
        dataSourceTree.addMouseListener(new TreeMouseAdapter());
    }

    private void decorateDataSourceTree() {
        decorator.setPanelBorder(BorderFactory.createEmptyBorder());
        decorator.setToolbarPosition(ActionToolbarPosition.TOP);
    }

    private void replaceTreeWithDecorated() {
        JPanel panel = decorator.createPanel();
        treePanel.add(panel);
    }

    private void showDataSources() {
        component.getDataSourceContainer().getDataSources()
                .forEach((dataSource) -> treeRoot.add(new PatchedDefaultMutableTreeNode(new DataSourceTreeNodeModel(dataSource))));
        treeModel.reload();
    }

    public void refreshDataSourcesMetadata() {
        Enumeration children = treeRoot.children();
        while (children.hasMoreElements()) {
            refreshDataSourceMetadata((PatchedDefaultMutableTreeNode) children.nextElement())
                    .thenAccept((isRefreshed) -> {
                        if (isRefreshed) {
                            treeModel.reload();
                        }
                    });
        }
    }

    public CompletableFuture<Boolean> refreshDataSourceMetadata(PatchedDefaultMutableTreeNode treeNode) {
        TreeNodeModelApi userObject = (TreeNodeModelApi) treeNode.getUserObject();
        DataSourceApi nodeDataSource = userObject.getDataSourceApi();
        if (nodeDataSource != null) {
            return dataSourceMetadataUpdateService.updateDataSourceMetadataUi(treeNode, nodeDataSource);
        } else {
            LOG.warn("Attempting to refresh data source metadata on a node that doesn't have metadata. This shouldn't have happened.");
            return completedFuture(false);
        }
    }

    public void createDataSource(DataSourceApi dataSource) {
        component.getDataSourceContainer().addDataSource(dataSource);
        TreeNodeModelApi model = new DataSourceTreeNodeModel(dataSource);
        PatchedDefaultMutableTreeNode treeNode = new PatchedDefaultMutableTreeNode(model);
        treeRoot.add(treeNode);
        refreshDataSourceMetadata(treeNode);
        treeModel.reload();
    }

    public void updateDataSource(PatchedDefaultMutableTreeNode treeNode, DataSourceApi oldDataSource, DataSourceApi newDataSource) {
        component.getDataSourceContainer().updateDataSource(oldDataSource, newDataSource);
        treeNode.setUserObject(new DataSourceTreeNodeModel(newDataSource));
        refreshDataSourceMetadata(treeNode);
        treeModel.reload();
    }

    public void removeDataSources(Project project, List<DataSourceApi> dataSourcesForRemoval) {
        component.getDataSourceContainer().removeDataSources(dataSourcesForRemoval);

        dataSourcesForRemoval.stream()
                .peek((dataSourceApi -> {
                    ApplicationManager.getApplication().runWriteAction(() -> {
                        try {
                            FileUtil.getDataSourceFile(project, dataSourceApi).delete(project);
                        } catch (IOException e) {
                            // do nothing
                        }
                    });
                }))
                .map(DataSourceApi::getName)
                .map(name -> {
                    Enumeration enumeration = treeRoot.children();
                    while (enumeration.hasMoreElements()) {
                        DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration.nextElement();
                        TreeNodeModelApi userObject = (TreeNodeModelApi) element.getUserObject();
                        DataSourceApi dataSource = userObject.getDataSourceApi();
                        if (dataSource.getName().equals(name)) {
                            return element;
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .forEach(treeRoot::remove);

        treeModel.reload();
    }

    @Override
    public void dispose() {
    }
}
