/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console.params;

import com.intellij.execution.console.ConsoleRootType;
import org.jetbrains.annotations.NotNull;

public class ParameterRootType extends ConsoleRootType {

    @NotNull
    public static ParameterRootType getInstance() {
        return findByClass(ParameterRootType.class);
    }

    ParameterRootType() {
        super("graphdb-parameters", "Graph Database parameters");
    }
}
