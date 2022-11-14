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

import static com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherNodeLookupImpl extends ASTWrapperPsiElement implements CypherNodeLookup {

  public CypherNodeLookupImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitNodeLookup(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CypherIdLookup getIdLookup() {
    return findChildByClass(CypherIdLookup.class);
  }

  @Override
  @Nullable
  public CypherIdentifiedIndexLookup getIdentifiedIndexLookup() {
    return findChildByClass(CypherIdentifiedIndexLookup.class);
  }

  @Override
  @Nullable
  public CypherIndexQuery getIndexQuery() {
    return findChildByClass(CypherIndexQuery.class);
  }

  @Override
  @NotNull
  public PsiElement getKNode() {
    return findNotNullChildByType(K_NODE);
  }

}
