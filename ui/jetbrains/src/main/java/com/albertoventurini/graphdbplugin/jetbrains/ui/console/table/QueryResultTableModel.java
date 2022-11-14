/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console.table;

import com.intellij.ui.treeStructure.Tree;

import javax.swing.table.DefaultTableModel;

public class QueryResultTableModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        Object valueAt = getValueAt(row, column);
        return valueAt != null && valueAt instanceof Tree;
    }
}
