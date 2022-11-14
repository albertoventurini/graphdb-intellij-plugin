/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.references.types;

import com.intellij.psi.PsiElement;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherType;

import static com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherSimpleType.ANY;

public interface CypherTypePropagator extends CypherTyped {

    @Override
    default CypherType getType() {
        PsiElement[] children = getChildren();
        if (children.length != 1) {
            return ANY;
        }

        PsiElement child = children[0];
        if (child instanceof CypherTyped) {
            return ((CypherTyped) child).getType();
        }
        return ANY;
    }

}
