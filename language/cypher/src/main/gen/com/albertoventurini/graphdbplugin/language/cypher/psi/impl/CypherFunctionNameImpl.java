/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.*;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherFunctionNameImpl extends ASTWrapperPsiElement implements CypherFunctionName {

  public CypherFunctionNameImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitFunctionName(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CypherEscapedSymbolicNameString getEscapedSymbolicNameString() {
    return findChildByClass(CypherEscapedSymbolicNameString.class);
  }

  @Override
  @Nullable
  public CypherUnescapedSymbolicNameString getUnescapedSymbolicNameString() {
    return findChildByClass(CypherUnescapedSymbolicNameString.class);
  }

  @Override
  @Nullable
  public PsiElement getKCount() {
    return findChildByType(CypherTypes.K_COUNT);
  }

}
