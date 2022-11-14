/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi;

import org.jetbrains.annotations.*;
import com.albertoventurini.graphdbplugin.language.cypher.references.CypherInvocation;

public interface CypherFunctionInvocation extends CypherInvocation {

  @Nullable
  CypherFunctionArguments getFunctionArguments();

  @NotNull
  CypherFunctionInvocationBody getFunctionInvocationBody();

  String getFullName();

}
