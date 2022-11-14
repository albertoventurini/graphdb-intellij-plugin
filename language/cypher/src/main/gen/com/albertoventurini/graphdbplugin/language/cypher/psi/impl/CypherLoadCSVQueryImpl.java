/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherLoadCSV;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherLoadCSVQuery;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherSingleQuery;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherLoadCSVQueryImpl extends ASTWrapperPsiElement implements CypherLoadCSVQuery {

  public CypherLoadCSVQueryImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitLoadCSVQuery(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CypherLoadCSV getLoadCSV() {
    return findNotNullChildByClass(CypherLoadCSV.class);
  }

  @Override
  @Nullable
  public CypherSingleQuery getSingleQuery() {
    return findChildByClass(CypherSingleQuery.class);
  }

}
