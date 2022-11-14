/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherNewParameter;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherSymbolicNameString;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherUnsignedInteger;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class CypherNewParameterImpl extends ASTWrapperPsiElement implements CypherNewParameter {

  public CypherNewParameterImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitNewParameter(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CypherSymbolicNameString getSymbolicNameString() {
    return findChildByClass(CypherSymbolicNameString.class);
  }

  @Override
  @Nullable
  public CypherUnsignedInteger getUnsignedInteger() {
    return findChildByClass(CypherUnsignedInteger.class);
  }

}
