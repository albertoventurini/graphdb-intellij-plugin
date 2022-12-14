package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;

public interface MetadataBuilder {
    DataSourceMetadata buildMetadata(DataSourceApi dataSource);
}
