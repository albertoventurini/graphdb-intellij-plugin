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
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherUpdatingClauseImpl extends ASTWrapperPsiElement implements CypherUpdatingClause {

  public CypherUpdatingClauseImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitUpdatingClause(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CypherCreate getCreate() {
    return findChildByClass(CypherCreate.class);
  }

  @Override
  @Nullable
  public CypherDelete getDelete() {
    return findChildByClass(CypherDelete.class);
  }

  @Override
  @Nullable
  public CypherForeach getForeach() {
    return findChildByClass(CypherForeach.class);
  }

  @Override
  @Nullable
  public CypherMerge getMerge() {
    return findChildByClass(CypherMerge.class);
  }

  @Override
  @Nullable
  public CypherRemove getRemove() {
    return findChildByClass(CypherRemove.class);
  }

  @Override
  @Nullable
  public CypherSetClause getSetClause() {
    return findChildByClass(CypherSetClause.class);
  }

}
