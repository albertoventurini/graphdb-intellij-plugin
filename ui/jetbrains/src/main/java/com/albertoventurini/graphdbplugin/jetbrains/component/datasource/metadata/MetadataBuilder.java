package com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;

interface MetadataBuilder {
    DataSourceMetadata buildMetadata(DataSourceApi dataSource);
}
