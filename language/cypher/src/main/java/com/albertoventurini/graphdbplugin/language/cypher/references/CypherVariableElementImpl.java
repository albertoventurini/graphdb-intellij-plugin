/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.references;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.ContributedReferenceHost;
import org.jetbrains.annotations.NotNull;

public abstract class CypherVariableElementImpl extends ASTWrapperPsiElement
        implements CypherVariableElement, ContributedReferenceHost {

    public CypherVariableElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
