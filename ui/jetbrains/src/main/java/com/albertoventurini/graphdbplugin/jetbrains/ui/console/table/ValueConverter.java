/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console.table;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.TreeMouseAdapter;
import com.albertoventurini.graphdbplugin.jetbrains.ui.helpers.UiHelper;
import com.albertoventurini.graphdbplugin.jetbrains.ui.renderes.tree.PropertyTreeCellRenderer;
import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.intellij.ui.treeStructure.Tree;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import java.util.Objects;

public class ValueConverter {

    private final TablePanel tablePanel;

    public ValueConverter(TablePanel tablePanel) {
        this.tablePanel = tablePanel;
    }

    public Object convert(String columnName, Object value, DataSourceApi dataSourceApi) {
        if (UiHelper.canBeTree(value)) {
            return createTree(UiHelper.keyValueToTreeNode(columnName, value, dataSourceApi, value));
        } else if (value instanceof String) {
            return UiHelper.representUiString((String) value);
        }

        return Objects.toString(value);
    }

    private Tree createTree(PatchedDefaultMutableTreeNode root) {
        DefaultTreeModel treeModel = new DefaultTreeModel(root, false);
        Tree tree = new Tree();
        tree.setModel(treeModel);
        tree.setCellRenderer(new PropertyTreeCellRenderer());
        tree.addTreeExpansionListener(new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                tablePanel.updateRowHeights();
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent event) {
                tablePanel.updateRowHeights();
            }
        });

        tree.addMouseListener(new TreeMouseAdapter());

        treeModel.reload();
        return tree;
    }
}
