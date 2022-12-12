package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherNewParameter;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherOldParameter;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherParameter;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class CypherParameterImplMixin
        extends ASTWrapperPsiElement implements CypherParameter {

    public CypherParameterImplMixin(@NotNull ASTNode node) {
        super(node);
    }

    public String getParameterName() {
        if (this.getNewParameter() != null) {
            CypherNewParameter newParameter = this.getNewParameter();
            if (newParameter.getSymbolicNameString() != null) {
                return newParameter.getSymbolicNameString().getUnescapedSymbolicNameString().getText();
            }
            if (newParameter.getUnsignedInteger() != null) {
                return newParameter.getUnsignedInteger().getText();
            }
        }

        if (this.getOldParameter() != null) {
            CypherOldParameter oldParameter = this.getOldParameter();
            if (oldParameter.getSymbolicNameString() != null) {
                return oldParameter.getSymbolicNameString().getUnescapedSymbolicNameString().getText();
            }
            if (oldParameter.getUnsignedInteger() != null) {
                return oldParameter.getUnsignedInteger().getText();
            }
        }

        return "graphbdb-plugin-invalid-parameter-name-should-never-happen";
    }

}
