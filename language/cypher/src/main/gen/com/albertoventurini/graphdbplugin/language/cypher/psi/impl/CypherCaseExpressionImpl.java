/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import java.util.List;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherCaseAlternatives;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherCaseExpression;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherExpression;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherCaseExpressionImpl extends ASTWrapperPsiElement implements CypherCaseExpression {

  public CypherCaseExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitCaseExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<CypherCaseAlternatives> getCaseAlternativesList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CypherCaseAlternatives.class);
  }

  @Override
  @NotNull
  public List<CypherExpression> getExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CypherExpression.class);
  }

  @Override
  @NotNull
  public PsiElement getKCase() {
    return findNotNullChildByType(K_CASE);
  }

  @Override
  @Nullable
  public PsiElement getKElse() {
    return findChildByType(K_ELSE);
  }

  @Override
  @NotNull
  public PsiElement getKEnd() {
    return findNotNullChildByType(K_END);
  }

}
