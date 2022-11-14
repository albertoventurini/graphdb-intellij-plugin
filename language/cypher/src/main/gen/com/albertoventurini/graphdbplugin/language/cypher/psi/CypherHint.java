/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CypherHint extends PsiElement {

  @Nullable
  CypherNodeLabel getNodeLabel();

  @Nullable
  CypherPropertyKeyName getPropertyKeyName();

  @NotNull
  List<CypherVariable> getVariableList();

  @Nullable
  PsiElement getKIndex();

  @Nullable
  PsiElement getKJoin();

  @Nullable
  PsiElement getKOn();

  @Nullable
  PsiElement getKScan();

  @NotNull
  PsiElement getKUsing();

}
