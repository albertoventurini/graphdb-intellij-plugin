/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherMergeAction;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherSetClause;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherMergeActionImpl extends ASTWrapperPsiElement implements CypherMergeAction {

  public CypherMergeActionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitMergeAction(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CypherSetClause getSetClause() {
    return findNotNullChildByClass(CypherSetClause.class);
  }

  @Override
  @Nullable
  public PsiElement getKCreate() {
    return findChildByType(CypherTypes.K_CREATE);
  }

  @Override
  @Nullable
  public PsiElement getKMatch() {
    return findChildByType(CypherTypes.K_MATCH);
  }

  @Override
  @NotNull
  public PsiElement getKOn() {
    return findNotNullChildByType(CypherTypes.K_ON);
  }

}
