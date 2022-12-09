/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.database.neo4j.bolt;

import com.albertoventurini.graphdbplugin.database.api.GraphDatabaseApi;
import com.albertoventurini.graphdbplugin.database.api.data.GraphMetadata;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResult;
import com.albertoventurini.graphdbplugin.database.neo4j.bolt.query.Neo4jBoltQueryResult;
import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.exceptions.ClientException;

import java.nio.channels.UnresolvedAddressException;
import java.util.Collections;
import java.util.Map;

/**
 * Communicates with Neo4j 3.0+ database using Bolt driver.
 */
public class Neo4jBoltDatabase implements GraphDatabaseApi {

    private final String url;
    private final AuthToken auth;
    private final SessionConfig dbConfig;

    public Neo4jBoltDatabase(Map<String, String> configuration) {
        this(new Neo4jBoltConfiguration(configuration));
    }

    public Neo4jBoltDatabase(Neo4jBoltConfiguration configuration) {
        final String host = configuration.getHost();
        final int port = configuration.getPort();
        final String username = configuration.getUser();
        final String password = configuration.getPassword();

        final String authType = configuration.getAuthType();
        final String protocol = configuration.getProtocol();

        if (protocol == null) {
            if (host.startsWith("bolt://") || host.startsWith("bolt+routing://")) {
                url = String.format("%s:%s", host, port);
            } else {
                url = String.format("bolt://%s:%s", host, port);
            }
        } else {
            url = "%s://%s:%s".formatted(protocol, host, port);
        }

        if (username != null && password != null) {
            auth = AuthTokens.basic(username, password);
        } else {
            auth = AuthTokens.none();
        }

        if (configuration.getDatabase() != null) {
          dbConfig = SessionConfig.forDatabase(configuration.getDatabase());
        } else {
          dbConfig = SessionConfig.defaultConfig();
        }
    }

    @Override
    public GraphQueryResult execute(String query) {
        return execute(query, Collections.emptyMap());
    }

    @Override
    public GraphQueryResult execute(String query, Map<String, Object> statementParameters) {
        try {
            // TODO: driver can be instantiated when this object is constructed. No need to create a new driver every time a query is executed.
            Driver driver = GraphDatabase.driver(url, auth);
            try {
                try (Session session = driver.session(dbConfig)) {

                    Neo4jBoltBuffer buffer = new Neo4jBoltBuffer();

                    long startTime = System.currentTimeMillis();
                    Result statementResult = session.run(query, statementParameters);
                    buffer.addColumns(statementResult.keys());

                    for (Record record : statementResult.list()) {
                        // Add row
                        buffer.addRow(record.asMap());
                    }
                    buffer.addResultSummary(statementResult.consume());
                    long endTime = System.currentTimeMillis();

                    return new Neo4jBoltQueryResult(endTime - startTime, buffer);
                }
            } finally {
                driver.closeAsync();
            }
        } catch (UnresolvedAddressException e) {
            throw new ClientException(e.getMessage());
        }
    }

    @Override
    public GraphMetadata metadata() {
        throw new IllegalStateException("Not implemented");
    }
}
