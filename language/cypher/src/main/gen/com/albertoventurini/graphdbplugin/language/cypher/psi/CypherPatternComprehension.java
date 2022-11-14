/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi;

import org.jetbrains.annotations.*;
import com.albertoventurini.graphdbplugin.language.cypher.references.types.CypherListYielding;

public interface CypherPatternComprehension extends CypherListYielding {

  @NotNull
  CypherExpression getExpression();

  @NotNull
  CypherRelationshipsPattern getRelationshipsPattern();

  @Nullable
  CypherWhere getWhere();

}
