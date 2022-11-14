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

public interface CypherSortItem extends PsiElement {

  @NotNull
  CypherExpression getExpression();

  @Nullable
  PsiElement getKAsc();

  @Nullable
  PsiElement getKAscending();

  @Nullable
  PsiElement getKDesc();

  @Nullable
  PsiElement getKDescending();

}
