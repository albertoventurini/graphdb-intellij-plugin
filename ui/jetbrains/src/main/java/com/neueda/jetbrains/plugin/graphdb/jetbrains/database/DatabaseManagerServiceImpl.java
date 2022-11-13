package com.neueda.jetbrains.plugin.graphdb.jetbrains.database;

import com.neueda.jetbrains.plugin.graphdb.database.api.GraphDatabaseApi;
import com.neueda.jetbrains.plugin.graphdb.database.neo4j.bolt.Neo4jBoltDatabase;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.component.datasource.DataSourceType;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.component.datasource.state.DataSourceApi;

public class DatabaseManagerServiceImpl implements DatabaseManagerService {

    public DatabaseManagerServiceImpl() {
    }

    public GraphDatabaseApi getDatabaseFor(final DataSourceApi dataSource) {
        if (dataSource.getDataSourceType() == DataSourceType.NEO4J_BOLT) {
            return new Neo4jBoltDatabase(dataSource.getConfiguration());
        }
        throw new RuntimeException(String.format("Database for data source [%s] does not exists",
                dataSource.getDataSourceType()));
    }
}
