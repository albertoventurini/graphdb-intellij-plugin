/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherDropRelationshipPropertyExistenceConstraint;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherRelationshipPropertyExistenceConstraintSyntax;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

import static com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherDropRelationshipPropertyExistenceConstraintImpl extends ASTWrapperPsiElement implements CypherDropRelationshipPropertyExistenceConstraint {

  public CypherDropRelationshipPropertyExistenceConstraintImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitDropRelationshipPropertyExistenceConstraint(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CypherRelationshipPropertyExistenceConstraintSyntax getRelationshipPropertyExistenceConstraintSyntax() {
    return findNotNullChildByClass(CypherRelationshipPropertyExistenceConstraintSyntax.class);
  }

  @Override
  @NotNull
  public PsiElement getKDrop() {
    return findNotNullChildByType(K_DROP);
  }

}
