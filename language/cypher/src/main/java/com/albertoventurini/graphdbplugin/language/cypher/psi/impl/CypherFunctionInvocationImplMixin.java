package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherFunctionInvocation;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class CypherFunctionInvocationImplMixin
    extends ASTWrapperPsiElement implements CypherFunctionInvocation {

    public CypherFunctionInvocationImplMixin(@NotNull final ASTNode node) {
        super(node);
    }

    @Override
    public String getFullName() {
        return this.getFunctionInvocationBody().getText();
    }
}
