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

public class CypherRelationshipPropertyExistenceConstraintSyntaxImpl extends ASTWrapperPsiElement implements CypherRelationshipPropertyExistenceConstraintSyntax {

  public CypherRelationshipPropertyExistenceConstraintSyntaxImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitRelationshipPropertyExistenceConstraintSyntax(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CypherPropertyExpression getPropertyExpression() {
    return findNotNullChildByClass(CypherPropertyExpression.class);
  }

  @Override
  @NotNull
  public CypherRelationshipPatternSyntax getRelationshipPatternSyntax() {
    return findNotNullChildByClass(CypherRelationshipPatternSyntax.class);
  }

  @Override
  @NotNull
  public PsiElement getKAssert() {
    return findNotNullChildByType(CypherTypes.K_ASSERT);
  }

  @Override
  @NotNull
  public PsiElement getKConstraint() {
    return findNotNullChildByType(CypherTypes.K_CONSTRAINT);
  }

  @Override
  @NotNull
  public PsiElement getKExists() {
    return findNotNullChildByType(CypherTypes.K_EXISTS);
  }

  @Override
  @NotNull
  public PsiElement getKOn() {
    return findNotNullChildByType(CypherTypes.K_ON);
  }

}
