/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console.table.editor;

import com.intellij.ui.treeStructure.Tree;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import java.awt.Component;

public class TreeModelTableCellEditor extends AbstractCellEditor implements TableCellEditor {

    private Tree tree;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        tree = (Tree) value;
        return tree;
    }

    @Override
    public Object getCellEditorValue() {
        return tree;
    }
}
