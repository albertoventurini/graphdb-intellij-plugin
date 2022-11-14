/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherExpression;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherSortItem;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherSortItemImpl extends ASTWrapperPsiElement implements CypherSortItem {

  public CypherSortItemImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitSortItem(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CypherExpression getExpression() {
    return findNotNullChildByClass(CypherExpression.class);
  }

  @Override
  @Nullable
  public PsiElement getKAsc() {
    return findChildByType(CypherTypes.K_ASC);
  }

  @Override
  @Nullable
  public PsiElement getKAscending() {
    return findChildByType(CypherTypes.K_ASCENDING);
  }

  @Override
  @Nullable
  public PsiElement getKDesc() {
    return findChildByType(CypherTypes.K_DESC);
  }

  @Override
  @Nullable
  public PsiElement getKDescending() {
    return findChildByType(CypherTypes.K_DESCENDING);
  }

}
