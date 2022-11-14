/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherMultiPartQuery;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherSinglePartQuery;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherSingleQuery;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherSingleQueryImpl extends ASTWrapperPsiElement implements CypherSingleQuery {

  public CypherSingleQueryImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitSingleQuery(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CypherMultiPartQuery getMultiPartQuery() {
    return findChildByClass(CypherMultiPartQuery.class);
  }

  @Override
  @Nullable
  public CypherSinglePartQuery getSinglePartQuery() {
    return findChildByClass(CypherSinglePartQuery.class);
  }

}
