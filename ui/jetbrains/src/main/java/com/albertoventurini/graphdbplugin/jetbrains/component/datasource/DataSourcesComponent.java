/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.component.datasource;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourcesComponentMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceContainer;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourcesComponentState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "GraphDatabaseSupport.DataSourcesState",
        storages = {@Storage("GraphDatabaseSupport_DataSourcesState.xml")})
public class DataSourcesComponent implements PersistentStateComponent<DataSourcesComponentState> {

    private final DataSourcesComponentMetadata componentMetadata;
    private DataSourcesComponentState state;

    public DataSourcesComponent(final Project project) {
        this.componentMetadata = project.getService(DataSourcesComponentMetadata.class);

        if (state == null) {
            state = new DataSourcesComponentState();
        }

        refreshAllMetadata();
    }

    public DataSourceContainer getDataSourceContainer() {
        state.migrate();
        return state.getCurrentContainer();
    }

    /**
     * Load persisted state.
     */
    @Override
    public void loadState(@NotNull final DataSourcesComponentState state) {
        this.state = state;
        refreshAllMetadata();
    }

    /**
     * Get state for persisting.
     */
    @Nullable
    @Override
    public DataSourcesComponentState getState() {
        return state;
    }

    public void refreshAllMetadata() {
        getDataSourceContainer().getDataSources().forEach(d -> {
            componentMetadata.getMetadata(d);
        });
    }
//
//    @NotNull
//    @Override
//    public String getComponentName() {
//        return "GraphDatabaseSupport.DataSources";
//    }
}
