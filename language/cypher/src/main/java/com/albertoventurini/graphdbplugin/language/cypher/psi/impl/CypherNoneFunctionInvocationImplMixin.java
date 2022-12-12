package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherNoneFunctionInvocation;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class CypherNoneFunctionInvocationImplMixin
    extends ASTWrapperPsiElement implements CypherNoneFunctionInvocation {

    public CypherNoneFunctionInvocationImplMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String getFullName() {
        return "none";
    }
}
