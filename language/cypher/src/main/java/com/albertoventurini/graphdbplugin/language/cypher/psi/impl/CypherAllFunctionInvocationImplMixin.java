package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherAllFunctionInvocation;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class CypherAllFunctionInvocationImplMixin
    extends ASTWrapperPsiElement implements CypherAllFunctionInvocation {

    public CypherAllFunctionInvocationImplMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String getFullName() {
        return "all";
    }
}
