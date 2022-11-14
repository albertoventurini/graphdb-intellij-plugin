/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.settings;

import com.intellij.openapi.application.ApplicationManager;

public interface SettingsComponent {

    static SettingsComponent getInstance() {
        return ApplicationManager.getApplication().getService(SettingsComponent.class);
    }

    String getUserId();

    boolean isGraphViewZoomInverted();

    void invertGraphViewZoom(boolean state);

    String getKnownPluginVersion();

    void setKnownPluginVersion(String version);
}
