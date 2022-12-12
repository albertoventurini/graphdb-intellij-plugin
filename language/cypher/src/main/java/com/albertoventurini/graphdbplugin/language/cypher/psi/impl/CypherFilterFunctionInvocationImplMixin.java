package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherFilterFunctionInvocation;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class CypherFilterFunctionInvocationImplMixin
    extends ASTWrapperPsiElement implements CypherFilterFunctionInvocation {

    public CypherFilterFunctionInvocationImplMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String getFullName() {
        return "filter";
    }
}
