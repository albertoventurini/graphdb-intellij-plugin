/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.actions.execute;

import com.intellij.icons.AllIcons;
import com.intellij.ide.browsers.BrowserLauncher;
import com.intellij.openapi.ui.Messages;

import java.awt.*;
import java.net.URI;

public class LandingPageAction {
    public static final String URL = "https://technologies.neueda.com/plugin";

    public static void open() {
        int ok = Messages.showOkCancelDialog("This feature is planned for a \nfuture release of the premium version.\n" +
                        "If you are interested, please visit: \n\n" + URL, "Premium Version", "Find more", "Cancel",
                AllIcons.General.QuestionDialog);
        if (ok == 0) {
            try {
                Desktop.getDesktop().browse(URI.create(URL));
            } catch (Exception e) {
                BrowserLauncher.getInstance().browse(URI.create(URL));
            }
        }
    }
}
