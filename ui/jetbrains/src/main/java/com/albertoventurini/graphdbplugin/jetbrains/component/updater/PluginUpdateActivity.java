/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.updater;

import com.intellij.notification.*;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.albertoventurini.graphdbplugin.jetbrains.component.settings.SettingsComponent;
import com.albertoventurini.graphdbplugin.jetbrains.util.PluginUtil;
import com.albertoventurini.graphdbplugin.platform.GraphBundle;
import com.albertoventurini.graphdbplugin.platform.GraphConstants;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.HyperlinkEvent;

public class PluginUpdateActivity implements StartupActivity, DumbAware {

    private static final String NOTIFICATION_ID = "GraphDatabaseSupportUpdateNotification";
    private boolean isUpdateNotificationShown = false;

    @Override
    public void runActivity(@NotNull Project project) {
        String currentVersion = PluginUtil.getVersion();
        String knownVersion = SettingsComponent.getInstance().getKnownPluginVersion();

        boolean isUpdated = !currentVersion.equals(knownVersion);
        if (isUpdated || GraphConstants.IS_DEVELOPMENT) {
            if (!isUpdateNotificationShown) {
                SettingsComponent.getInstance().setKnownPluginVersion(currentVersion);
                showNotification(project, currentVersion);
                isUpdateNotificationShown = true;
            }
        }
    }

    private void showNotification(Project project, String currentVersion) {
        NotificationGroup group = NotificationGroupManager.getInstance()
                .getNotificationGroup("GraphDatabaseSupportUpdateNotification");

        Notification notification = group.createNotification(
                GraphBundle.message("updater.title", currentVersion),
                GraphBundle.message("updater.notification"),
                NotificationType.INFORMATION
        );

        Notifications.Bus.notify(notification, project);
    }
}
