/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.platform;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.PropertyKey;

import java.util.ResourceBundle;

public class GraphBundle {

    private static final String BUNDLE_NAME = "graphdb.messages.GraphBundle";

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    public static String message(@PropertyKey(resourceBundle = BUNDLE_NAME) String key, Object... params) {
        return AbstractBundle.message(BUNDLE, key, params);
    }

    public static String messageOrDefault(@PropertyKey(resourceBundle = BUNDLE_NAME) String key, String defaultValue,
                                          Object... params) {
        return AbstractBundle.messageOrDefault(BUNDLE, key, defaultValue, params);
    }

}
