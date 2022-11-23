/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.settings;

import com.intellij.ide.util.PropertiesComponent;

public class SettingsComponentImpl implements SettingsComponent {

    private static final String KNOWN_PLUGIN_VERSION = "GraphDbPlugin.KnownPluginVersion";
    private static final String GRAPH_VIEW_INVERT_ZOOM = "GraphDbPlugin.GraphViewInvertZoom";

    @Override
    public boolean isGraphViewZoomInverted() {
        return properties().getBoolean(GRAPH_VIEW_INVERT_ZOOM, true);
    }

    @Override
    public void invertGraphViewZoom(boolean state) {
        properties().setValue(GRAPH_VIEW_INVERT_ZOOM, state, true);
    }

    @Override
    public String getKnownPluginVersion() {
        return properties().getValue(KNOWN_PLUGIN_VERSION, "unknown");
    }

    @Override
    public void setKnownPluginVersion(String version) {
        properties().setValue(KNOWN_PLUGIN_VERSION, version);
    }

    private PropertiesComponent properties() {
        return PropertiesComponent.getInstance();
    }
}
