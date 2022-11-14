/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.mocks.services;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.testFramework.LightPlatformTestCase;

import org.picocontainer.MutablePicoContainer;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class DummyExecutorService implements ExecutorService {

    public static void register() {
        LightPlatformTestCase.initApplication();
        MutablePicoContainer container = (MutablePicoContainer) ApplicationManager.getApplication().getPicoContainer();

        container.unregisterComponent(ExecutorService.class.getName());
        container.registerComponentInstance(ExecutorService.class.getName(), new DummyExecutorService());
    }

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
