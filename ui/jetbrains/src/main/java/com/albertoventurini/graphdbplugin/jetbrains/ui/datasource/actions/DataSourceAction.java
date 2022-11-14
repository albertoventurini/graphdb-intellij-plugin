/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.actions;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.util.FileUtil;
import com.albertoventurini.graphdbplugin.jetbrains.util.Notifier;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;

public class DataSourceAction extends AnAction {

    private final DataSourceApi dataSource;

    DataSourceAction(String title, String description, Icon icon, DataSourceApi dataSource) {
        super(title, description, icon);
        this.dataSource = dataSource;
    }

    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        final Project project = getEventProject(e);
        try {
            FileUtil.openFile(project, FileUtil.getDataSourceFile(project, dataSource));
        } catch (IOException exception) {
            Notifier.error("Open editor error", exception.getMessage());
        }
    }
}
