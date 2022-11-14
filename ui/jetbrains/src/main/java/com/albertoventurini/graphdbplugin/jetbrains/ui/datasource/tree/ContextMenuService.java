/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree;

import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.dto.ContextMenu;
import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.Optional;

public class ContextMenuService {

    public Optional<ContextMenu> getContextMenu(TreePath path) {
        if (path != null) {
            TreeNode lastPathComponent = (TreeNode) path.getLastPathComponent();

            Optional<TreeNodeModelApi> model = extractUserObject(lastPathComponent);

            return model.flatMap(TreeNodeModelApi::getContextMenu);
        }
        return Optional.empty();
    }

    private Optional<TreeNodeModelApi> extractUserObject(TreeNode node) {
        if (node instanceof PatchedDefaultMutableTreeNode) {
            return Optional.of((TreeNodeModelApi) ((PatchedDefaultMutableTreeNode) node).getUserObject());
        }
        return Optional.empty();
    }
}
