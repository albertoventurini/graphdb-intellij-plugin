/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.settings;

import com.intellij.openapi.application.ApplicationManager;

/**
 * This service allows persisting and reading configuration settings.
 */
public interface SettingsComponent {

    static SettingsComponent getInstance() {
        return ApplicationManager.getApplication().getService(SettingsComponent.class);
    }

    boolean isGraphViewZoomInverted();

    void invertGraphViewZoom(boolean state);

    String getKnownPluginVersion();

    void setKnownPluginVersion(String version);
}
