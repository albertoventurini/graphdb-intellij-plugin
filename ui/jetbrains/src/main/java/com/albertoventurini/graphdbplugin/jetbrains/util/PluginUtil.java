/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.util;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;
import com.albertoventurini.graphdbplugin.platform.GraphConstants;

public class PluginUtil {

    private static IdeaPluginDescriptor plugin;

    public static String getVersion() {
        return plugin().getVersion();
    }

    public static boolean isEnabled() {
        return plugin().isEnabled();
    }

    private static IdeaPluginDescriptor plugin() {
        if (plugin == null) {
            plugin = PluginManager.getPlugin(PluginId.getId(GraphConstants.PLUGIN_ID));
        }
        return plugin;
    }
}
