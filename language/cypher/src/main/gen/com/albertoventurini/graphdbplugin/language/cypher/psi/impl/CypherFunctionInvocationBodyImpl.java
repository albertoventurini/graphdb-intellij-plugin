/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherFunctionInvocationBody;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherFunctionName;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherNamespace;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherFunctionInvocationBodyImpl extends ASTWrapperPsiElement implements CypherFunctionInvocationBody {

  public CypherFunctionInvocationBodyImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitFunctionInvocationBody(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CypherFunctionName getFunctionName() {
    return findNotNullChildByClass(CypherFunctionName.class);
  }

  @Override
  @NotNull
  public CypherNamespace getNamespace() {
    return findNotNullChildByClass(CypherNamespace.class);
  }

}
