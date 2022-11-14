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

public interface CypherMatch extends PsiElement {

  @NotNull
  List<CypherHint> getHintList();

  @NotNull
  CypherPattern getPattern();

  @Nullable
  CypherWhere getWhere();

  @NotNull
  PsiElement getKMatch();

  @Nullable
  PsiElement getKOptional();

}
