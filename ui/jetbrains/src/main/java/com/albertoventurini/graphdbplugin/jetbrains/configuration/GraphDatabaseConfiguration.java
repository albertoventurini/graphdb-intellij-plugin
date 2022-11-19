/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.configuration;

import com.albertoventurini.graphdbplugin.jetbrains.component.settings.SettingsComponent;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.event.PluginSettingsUpdated;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.ui.components.JBCheckBox;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GraphDatabaseConfiguration implements Configurable {

    private boolean isModified = false;
    private JBCheckBox invertZoomCheckBox;

    @Nls
    @Override
    public String getDisplayName() {
        return "Graph Database";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "Graph Database plugin configuration";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        isModified = false;

        invertZoomCheckBox = new JBCheckBox("Invert mouse wheel zoom controls in Graph View",
                SettingsComponent.getInstance().isGraphViewZoomInverted());
        invertZoomCheckBox.addActionListener(e -> isModified = true);

        JPanel rootPane = new JPanel();
        rootPane.setLayout(new BoxLayout(rootPane, BoxLayout.Y_AXIS));
        rootPane.add(invertZoomCheckBox);

        return rootPane;
    }

    @Override
    public boolean isModified() {
        return isModified;
    }

    @Override
    public void apply() {
        SettingsComponent instance = SettingsComponent.getInstance();
        instance.invertGraphViewZoom(invertZoomCheckBox.isSelected());

        ApplicationManager.getApplication().getMessageBus().syncPublisher(PluginSettingsUpdated.TOPIC).settingsUpdated();

        isModified = false;
    }

    @Override
    public void reset() {
        invertZoomCheckBox.setSelected(SettingsComponent.getInstance().isGraphViewZoomInverted());
        isModified = false;
    }

    @Override
    public void disposeUIResources() {
    }
}
