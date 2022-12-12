package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherReduceFunctionInvocation;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class CypherReduceFunctionInvocationImplMixin
    extends ASTWrapperPsiElement implements CypherReduceFunctionInvocation {

    public CypherReduceFunctionInvocationImplMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String getFullName() {
        return "reduce";
    }
}
