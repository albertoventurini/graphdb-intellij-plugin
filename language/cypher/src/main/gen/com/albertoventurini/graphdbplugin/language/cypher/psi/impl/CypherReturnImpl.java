/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherReturn;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherReturnBody;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherReturnImpl extends ASTWrapperPsiElement implements CypherReturn {

  public CypherReturnImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitReturn(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CypherReturnBody getReturnBody() {
    return findNotNullChildByClass(CypherReturnBody.class);
  }

  @Override
  @Nullable
  public PsiElement getKDistinct() {
    return findChildByType(CypherTypes.K_DISTINCT);
  }

  @Override
  @NotNull
  public PsiElement getKReturn() {
    return findNotNullChildByType(CypherTypes.K_RETURN);
  }

}
