/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.visualization.services;

import java.awt.*;

public interface LookAndFeelService {

    Color getBackgroundColor();
    Color getBorderColor();
    Color getNodeStrokeColor();
    Color getNodeStrokeHoverColor();
    Color getNodeFillColor();
    Color getNodeFillHoverColor();
    Color getEdgeStrokeColor();
    Color getEdgeFillColor();

    Color getTextColor();

    boolean isGraphViewZoomInverted();
}
