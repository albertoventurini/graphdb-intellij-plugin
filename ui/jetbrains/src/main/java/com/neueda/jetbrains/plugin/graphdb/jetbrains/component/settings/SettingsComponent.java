package com.neueda.jetbrains.plugin.graphdb.jetbrains.component.settings;

import com.intellij.openapi.application.ApplicationManager;

public interface SettingsComponent {

    static SettingsComponent getInstance() {
        return ApplicationManager.getApplication().getService(SettingsComponent.class);
    }

    String getUserId();

    boolean isAnalyticEnabled();

    void enableAnalytics(boolean state);

    boolean isGraphViewZoomInverted();

    void invertGraphViewZoom(boolean state);

    String getKnownPluginVersion();

    void setKnownPluginVersion(String version);
}
