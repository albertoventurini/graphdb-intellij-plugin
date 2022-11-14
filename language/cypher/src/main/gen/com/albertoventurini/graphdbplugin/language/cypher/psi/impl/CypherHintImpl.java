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
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherHintImpl extends ASTWrapperPsiElement implements CypherHint {

  public CypherHintImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitHint(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CypherNodeLabel getNodeLabel() {
    return findChildByClass(CypherNodeLabel.class);
  }

  @Override
  @Nullable
  public CypherPropertyKeyName getPropertyKeyName() {
    return findChildByClass(CypherPropertyKeyName.class);
  }

  @Override
  @NotNull
  public List<CypherVariable> getVariableList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CypherVariable.class);
  }

  @Override
  @Nullable
  public PsiElement getKIndex() {
    return findChildByType(CypherTypes.K_INDEX);
  }

  @Override
  @Nullable
  public PsiElement getKJoin() {
    return findChildByType(CypherTypes.K_JOIN);
  }

  @Override
  @Nullable
  public PsiElement getKOn() {
    return findChildByType(CypherTypes.K_ON);
  }

  @Override
  @Nullable
  public PsiElement getKScan() {
    return findChildByType(CypherTypes.K_SCAN);
  }

  @Override
  @NotNull
  public PsiElement getKUsing() {
    return findNotNullChildByType(CypherTypes.K_USING);
  }

}
