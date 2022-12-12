package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherSingleFunctionInvocation;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class CypherSingleFunctionInvocationImplMixin
    extends ASTWrapperPsiElement implements CypherSingleFunctionInvocation {

    public CypherSingleFunctionInvocationImplMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String getFullName() {
        return "single";
    }
}
