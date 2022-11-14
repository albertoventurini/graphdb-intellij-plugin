/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import java.util.List;

import com.albertoventurini.graphdbplugin.language.cypher.psi.*;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherSinglePartQueryImpl extends ASTWrapperPsiElement implements CypherSinglePartQuery {

  public CypherSinglePartQueryImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitSinglePartQuery(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<CypherReadingClause> getReadingClauseList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CypherReadingClause.class);
  }

  @Override
  @Nullable
  public CypherReadingWithReturn getReadingWithReturn() {
    return findChildByClass(CypherReadingWithReturn.class);
  }

  @Override
  @Nullable
  public CypherReturn getReturn() {
    return findChildByClass(CypherReturn.class);
  }

  @Override
  @NotNull
  public List<CypherUpdatingClause> getUpdatingClauseList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CypherUpdatingClause.class);
  }

}
