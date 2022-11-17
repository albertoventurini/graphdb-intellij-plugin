/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.mocks.services;

import com.albertoventurini.graphdbplugin.jetbrains.services.ExecutorService;
import com.intellij.openapi.application.ModalityState;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class DummyExecutorService implements ExecutorService {

    @Override
    public <T> void runInBackground(Callable<T> task, Consumer<T> onSuccess, Consumer<Exception> onFailure) {
        try {
            T result = task.call();
            onSuccess.accept(result);
        } catch (Exception e) {
            onFailure.accept(e);
        }
    }

    @Override
    public <T> void runInBackground(Callable<T> task, Consumer<T> onSuccess, Consumer<Exception> onFailure, ModalityState modalityState) {
        runInBackground(task, onSuccess, onFailure);
    }
}
