package com.albertoventurini.graphdbplugin.test.integration.neo4j.util.server;

import java.util.concurrent.CompletableFuture;

/**
 * Base class for resources that can be started asynchronously.
 * Each implementation must define the actual logic to start the resource
 * by overriding the `startAsync` method.
 * @param <T> the type of the resource
 */
abstract class AsyncStartable<T> implements Startable {

    private volatile T startedInstance;

    private CompletableFuture<T> future;

    /**
     * Initialise the resource asynchronously.
     * @return a {@link CompletableFuture<T>} which will yield the desired resource once completed
     */
    protected abstract CompletableFuture<T> startAsync();

    @Override
    public final void start() {
        future = startAsync();
    }

    /**
     * Get the started resource, possibly waiting for initialisation to finish.
     * @return the started resource
     */
    protected T getStartedInstance() {
        if (startedInstance == null) {
            synchronized (this) {
                if (startedInstance == null) {
                    try {
                        startedInstance = future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return startedInstance;
    }
}
