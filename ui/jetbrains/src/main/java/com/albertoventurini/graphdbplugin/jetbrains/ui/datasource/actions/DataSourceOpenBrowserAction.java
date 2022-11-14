/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.actions;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.albertoventurini.graphdbplugin.database.neo4j.bolt.Neo4jBoltConfiguration;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DataSourceOpenBrowserAction extends AnAction {

    private DataSourceApi dataSource;

    DataSourceOpenBrowserAction(String title, String description, Icon icon, DataSourceApi dataSource) {
        super(title, description, icon);
        this.dataSource = dataSource;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            String host = dataSource.getConfiguration().get(Neo4jBoltConfiguration.HOST);
            openWebpage(new URI("http://" + host + ":7474"));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    private void openWebpage(URI uri) throws IOException {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            desktop.browse(uri);
        }
    }

}
