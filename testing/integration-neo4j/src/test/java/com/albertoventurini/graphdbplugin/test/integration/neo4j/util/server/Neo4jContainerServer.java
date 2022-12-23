package com.albertoventurini.graphdbplugin.test.integration.neo4j.util.server;

import com.albertoventurini.graphdbplugin.test.database.neo4j.common.Neo4jServer;
import org.testcontainers.containers.Neo4jContainer;

import java.util.concurrent.CompletableFuture;

/**
 * An implementation of {@link Neo4jServer} running in a test container.
 */
final class Neo4jContainerServer extends AsyncStartable<Neo4jContainer<?>> implements Neo4jServer {

    private final String versionTag;

    private static final int BOLT_PORT = 7687;

    Neo4jContainerServer(final String versionTag) {
        this.versionTag = versionTag;
    }

    @Override
    protected CompletableFuture<Neo4jContainer<?>> startAsync() {
        return CompletableFuture.supplyAsync(() -> {
            final var neo4jContainer = new Neo4jContainer<>("neo4j:" + versionTag)
                    .withoutAuthentication();
            neo4jContainer.start();
            return neo4jContainer;
        });
    }

    @Override
    public String getBoltHost() {
        return getStartedInstance().getHost();
    }

    @Override
    public String getBoltPort() {
        return String.valueOf(getStartedInstance().getMappedPort(BOLT_PORT));
    }
}
