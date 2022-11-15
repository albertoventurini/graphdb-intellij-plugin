/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.completion.providers;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.CypherMetadataProviderService;

public abstract class BaseCompletionProvider extends CompletionProvider<CompletionParameters> {

    protected void withCypherMetadataProvider(CompletionParameters parameters, ProjectRunnable runnable) {
        Project project = parameters.getEditor().getProject();
        if (project != null) {
            CypherMetadataProviderService provider = project.getService(CypherMetadataProviderService.class);
            runnable.run(provider);
        }
    }

    public interface ProjectRunnable {
        void run(CypherMetadataProviderService metadataProvider);
    }
}
