/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.services;

import com.albertoventurini.graphdbplugin.jetbrains.component.settings.SettingsComponent;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.UIUtil;
import com.albertoventurini.graphdbplugin.visualization.services.LookAndFeelService;

import javax.swing.*;
import java.awt.*;

public class IdeaLookAndFeelService implements LookAndFeelService {

    @Override
    public Color getBackgroundColor() {
        return UIUtil.getPanelBackground();
    }

    @Override
    public Color getBorderColor() {
        return JBColor.border();
    }

    @Override
    public Color getEdgeStrokeColor() {
        return UIUtil.getBoundsColor().darker();
    }

    @Override
    public Color getEdgeFillColor() {
        return UIUtil.getBoundsColor().darker();
    }

    @Override
    public Color getNodeStrokeColor() {
        return UIUtil.getBoundsColor().darker();
    }

    @Override
    public Color getNodeStrokeHoverColor() {
        return UIUtil.getBoundsColor().darker();
    }

    @Override
    public Color getNodeFillColor() {
        return UIManager.getColor("InternalFrame.inactiveTitleBackground");
    }

    @Override
    public Color getNodeFillHoverColor() {
        return UIManager.getColor("InternalFrame.activeTitleBackground");
    }

    @Override
    public Color getTextColor() {
        return UIManager.getColor("text");
    }

    @Override
    public boolean isGraphViewZoomInverted() {
        return SettingsComponent.getInstance().isGraphViewZoomInverted();
    }

}
