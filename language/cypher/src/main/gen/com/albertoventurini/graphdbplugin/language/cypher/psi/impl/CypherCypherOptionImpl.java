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

public class CypherCypherOptionImpl extends ASTWrapperPsiElement implements CypherCypherOption {

  public CypherCypherOptionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitCypherOption(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<CypherConfigurationOption> getConfigurationOptionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CypherConfigurationOption.class);
  }

  @Override
  @Nullable
  public CypherVersionNumber getVersionNumber() {
    return findChildByClass(CypherVersionNumber.class);
  }

  @Override
  @NotNull
  public PsiElement getKCypher() {
    return findNotNullChildByType(CypherTypes.K_CYPHER);
  }

}
