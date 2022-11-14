/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.dto;


import javax.swing.Icon;

public class ValueWithIcon {

    private final Icon icon;
    private final String value;

    public ValueWithIcon(Icon icon, String value) {
        this.icon = icon;
        this.value = value;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getValue() {
        return value;
    }
}
