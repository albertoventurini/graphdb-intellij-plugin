/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.completion.providers;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.openapi.project.Project;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.CypherMetadataProviderService;

abstract class BaseCompletionProvider extends CompletionProvider<CompletionParameters> {

    protected void withCypherMetadataProvider(
            final CompletionParameters parameters,
            final ProjectRunnable runnable) {
        final Project project = parameters.getEditor().getProject();
        if (project != null) {
            final CypherMetadataProviderService provider = project.getService(CypherMetadataProviderService.class);
            runnable.run(provider);
        }
    }

    interface ProjectRunnable {
        void run(CypherMetadataProviderService metadataProvider);
    }
}
