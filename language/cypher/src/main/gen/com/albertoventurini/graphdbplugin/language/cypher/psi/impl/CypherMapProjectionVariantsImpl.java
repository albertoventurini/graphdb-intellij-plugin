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

public class CypherMapProjectionVariantsImpl extends ASTWrapperPsiElement implements CypherMapProjectionVariants {

  public CypherMapProjectionVariantsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitMapProjectionVariants(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CypherAllPropertiesSelector getAllPropertiesSelector() {
    return findChildByClass(CypherAllPropertiesSelector.class);
  }

  @Override
  @Nullable
  public CypherLiteralEntry getLiteralEntry() {
    return findChildByClass(CypherLiteralEntry.class);
  }

  @Override
  @Nullable
  public CypherPropertySelector getPropertySelector() {
    return findChildByClass(CypherPropertySelector.class);
  }

  @Override
  @Nullable
  public CypherVariableSelector getVariableSelector() {
    return findChildByClass(CypherVariableSelector.class);
  }

}
