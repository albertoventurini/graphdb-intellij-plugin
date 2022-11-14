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

public interface CypherCommand extends PsiElement {

  @Nullable
  CypherCreateIndex getCreateIndex();

  @Nullable
  CypherCreateNodePropertyExistenceConstraint getCreateNodePropertyExistenceConstraint();

  @Nullable
  CypherCreateRelationshipPropertyExistenceConstraint getCreateRelationshipPropertyExistenceConstraint();

  @Nullable
  CypherCreateUniqueConstraint getCreateUniqueConstraint();

  @Nullable
  CypherDropIndex getDropIndex();

  @Nullable
  CypherDropNodePropertyExistenceConstraint getDropNodePropertyExistenceConstraint();

  @Nullable
  CypherDropRelationshipPropertyExistenceConstraint getDropRelationshipPropertyExistenceConstraint();

  @Nullable
  CypherDropUniqueConstraint getDropUniqueConstraint();

}
