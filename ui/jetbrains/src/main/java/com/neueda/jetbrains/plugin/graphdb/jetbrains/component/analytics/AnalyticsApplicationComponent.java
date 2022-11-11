package com.neueda.jetbrains.plugin.graphdb.jetbrains.component.analytics;

import com.intellij.openapi.application.ApplicationManager;

public interface AnalyticsApplicationComponent {

    static AnalyticsApplicationComponent getInstance() {
        return ApplicationManager.getApplication().getComponent(AnalyticsApplicationComponent.class);
    }

    void event(String component, String action);

    void forceEvent(String component, String action);
}
