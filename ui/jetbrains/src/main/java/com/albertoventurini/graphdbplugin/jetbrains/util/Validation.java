/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.util;

import com.intellij.openapi.ui.ValidationInfo;

import javax.swing.JComponent;

public class Validation {

    public static ValidationInfo validation(String message, JComponent component) {
        return new ValidationInfo(message, component);
    }

    public static ValidationInfo warning(String message, JComponent component) {
        return new ValidationInfo(message, component).asWarning().withOKEnabled();
    }
}
