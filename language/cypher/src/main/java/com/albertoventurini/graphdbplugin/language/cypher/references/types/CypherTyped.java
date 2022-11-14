/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.references.types;

import com.intellij.psi.PsiElement;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherType;

public interface CypherTyped extends PsiElement {

    CypherType getType();

}
