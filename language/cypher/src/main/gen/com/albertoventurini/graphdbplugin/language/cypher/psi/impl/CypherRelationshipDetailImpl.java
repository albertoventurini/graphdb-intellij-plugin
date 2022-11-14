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

public class CypherRelationshipDetailImpl extends ASTWrapperPsiElement implements CypherRelationshipDetail {

  public CypherRelationshipDetailImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitRelationshipDetail(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CypherMaybeVariableLength getMaybeVariableLength() {
    return findChildByClass(CypherMaybeVariableLength.class);
  }

  @Override
  @Nullable
  public CypherProperties getProperties() {
    return findChildByClass(CypherProperties.class);
  }

  @Override
  @Nullable
  public CypherRelationshipTypes getRelationshipTypes() {
    return findChildByClass(CypherRelationshipTypes.class);
  }

  @Override
  @Nullable
  public CypherVariable getVariable() {
    return findChildByClass(CypherVariable.class);
  }

}
