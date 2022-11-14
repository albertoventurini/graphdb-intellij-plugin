/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.albertoventurini.graphdbplugin.language.cypher.references.CypherInvocation;

public interface CypherExtractFunctionInvocation extends CypherInvocation {

  @Nullable
  CypherExpression getExpression();

  @NotNull
  CypherFilterExpression getFilterExpression();

  @NotNull
  PsiElement getKExtract();

  String getFullName();

}
