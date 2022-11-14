/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.services;

import com.intellij.openapi.application.ModalityState;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public interface ExecutorService {
    <T> void runInBackground(Callable<T> task, Consumer<T> onSuccess, Consumer<Exception> onFailure);

    <T> void runInBackground(Callable<T> task, Consumer<T> onSuccess, Consumer<Exception> onFailure, ModalityState modalityState);
}
