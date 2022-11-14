/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.visualization.util;

import prefuse.Display;
import prefuse.Visualization;
import prefuse.util.display.DisplayLib;

import java.awt.geom.Rectangle2D;

public class PrefuseUtil {

    public static final int DURATION = 0;

    public static void zoomAndPanToFit(Visualization visualization, Display display) {
        Rectangle2D bounds = visualization.getBounds(Visualization.ALL_ITEMS);

        if (bounds.getWidth() == 0 && bounds.getHeight() == 0) {
            return;
        }

        DisplayLib.fitViewToBounds(display, bounds, DURATION);
    }
}
