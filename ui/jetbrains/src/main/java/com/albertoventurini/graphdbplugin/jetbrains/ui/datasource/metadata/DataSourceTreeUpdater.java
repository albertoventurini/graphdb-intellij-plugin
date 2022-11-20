package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourceMetadata;
import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;

/**
 * Updates the data source tree according to the provided data source metadata.
 */
interface DataSourceTreeUpdater {

    /**
     * Given a tree root and a datasource metadata object, this method
     * displays the data on the tree.
     * @param metadata the metadata
     */
     void updateTree(PatchedDefaultMutableTreeNode dataSourceRootTreeNode, DataSourceMetadata metadata);
}
