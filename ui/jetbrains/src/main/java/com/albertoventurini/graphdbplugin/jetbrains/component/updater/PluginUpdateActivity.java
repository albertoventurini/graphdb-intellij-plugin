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

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Displays a notification when the plugin was updated to a new version.
 */
public class PluginUpdateActivity implements StartupActivity, DumbAware {

    private final AtomicBoolean isUpdateNotificationShown = new AtomicBoolean(false);

    @Override
    public void runActivity(@NotNull final Project project) {
        final String currentVersion = PluginUtil.getVersion();
        final String knownVersion = SettingsComponent.getInstance().getKnownPluginVersion();

        final boolean isUpdated = !currentVersion.equals(knownVersion);
        if (isUpdated || GraphConstants.IS_DEVELOPMENT) {
            if (isUpdateNotificationShown.compareAndSet(false, true)) {
                SettingsComponent.getInstance().setKnownPluginVersion(currentVersion);
                showNotification(project, currentVersion);
            }
        }
    }

    private void showNotification(@NotNull final Project project, String currentVersion) {
        final NotificationGroup group = NotificationGroupManager.getInstance()
                .getNotificationGroup("graphdbplugin.notifications.update");

        final Notification notification = group.createNotification(
                GraphBundle.message("updater.title", currentVersion),
                GraphBundle.message("updater.notification"),
                NotificationType.INFORMATION
        );

        Notifications.Bus.notify(notification, project);
    }
}
