package com.neueda.jetbrains.plugin.graphdb.jetbrains.ui.datasource.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.component.datasource.state.DataSourceApi;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.util.FileUtil;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.util.Notifier;
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
