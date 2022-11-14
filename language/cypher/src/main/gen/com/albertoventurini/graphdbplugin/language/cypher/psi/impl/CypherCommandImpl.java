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

public class CypherCommandImpl extends ASTWrapperPsiElement implements CypherCommand {

  public CypherCommandImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitCommand(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CypherCreateIndex getCreateIndex() {
    return findChildByClass(CypherCreateIndex.class);
  }

  @Override
  @Nullable
  public CypherCreateNodePropertyExistenceConstraint getCreateNodePropertyExistenceConstraint() {
    return findChildByClass(CypherCreateNodePropertyExistenceConstraint.class);
  }

  @Override
  @Nullable
  public CypherCreateRelationshipPropertyExistenceConstraint getCreateRelationshipPropertyExistenceConstraint() {
    return findChildByClass(CypherCreateRelationshipPropertyExistenceConstraint.class);
  }

  @Override
  @Nullable
  public CypherCreateUniqueConstraint getCreateUniqueConstraint() {
    return findChildByClass(CypherCreateUniqueConstraint.class);
  }

  @Override
  @Nullable
  public CypherDropIndex getDropIndex() {
    return findChildByClass(CypherDropIndex.class);
  }

  @Override
  @Nullable
  public CypherDropNodePropertyExistenceConstraint getDropNodePropertyExistenceConstraint() {
    return findChildByClass(CypherDropNodePropertyExistenceConstraint.class);
  }

  @Override
  @Nullable
  public CypherDropRelationshipPropertyExistenceConstraint getDropRelationshipPropertyExistenceConstraint() {
    return findChildByClass(CypherDropRelationshipPropertyExistenceConstraint.class);
  }

  @Override
  @Nullable
  public CypherDropUniqueConstraint getDropUniqueConstraint() {
    return findChildByClass(CypherDropUniqueConstraint.class);
  }

}
