package com.neueda.jetbrains.plugin.graphdb.jetbrains.ui.datasource.actions;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.component.datasource.DataSourceType;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.component.datasource.state.DataSourceApi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DataSourceActionGroup extends ActionGroup {

    private final DataSourceApi dataSourceApi;

    public DataSourceActionGroup(final DataSourceApi dataSourceApi) {
        this.dataSourceApi = dataSourceApi;
    }

    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable final AnActionEvent e) {
        if (dataSourceApi.getDataSourceType() == DataSourceType.NEO4J_BOLT) {
            return new AnAction[]{
                    new DataSourceAction("Open editor", "", null, dataSourceApi),
                    new DataSourceOpenBrowserAction("Open in browser", "", null, dataSourceApi),
                    new CreateNodeAction("Create new node", dataSourceApi)
            };
        }
        throw new IllegalStateException("Unknown data source type encountered: " + dataSourceApi.getDataSourceType());
    }
}
