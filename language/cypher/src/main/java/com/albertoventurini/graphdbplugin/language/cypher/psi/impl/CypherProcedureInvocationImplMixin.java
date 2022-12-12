package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherProcedureInvocation;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class CypherProcedureInvocationImplMixin
    extends ASTWrapperPsiElement implements CypherProcedureInvocation {

    public CypherProcedureInvocationImplMixin(@NotNull final ASTNode node) {
        super(node);
    }

    @Override
    public String getFullName() {
        return this.getProcedureInvocationBody().getText();
    }
}
