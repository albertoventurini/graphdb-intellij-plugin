/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.references;

import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherList;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherType;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherMaybeVariableLength;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherNodePattern;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherPatternPart;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherRelationshipDetail;
import com.albertoventurini.graphdbplugin.language.cypher.references.types.CypherTyped;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherSimpleType.*;
import static java.util.Objects.nonNull;

public interface CypherVariableElement extends CypherNamedElement, CypherTyped {

    @Nullable
    @Override
    default PsiElement getNameIdentifier() {
        return this;
    }

    @Override
    default CypherType getType() {
        return Optional.ofNullable(getReferences())
                .filter(reference -> reference.length > 0)
                .map(reference -> reference[0])
                .map(PsiReference::resolve)
                .map(PsiElement::getParent)
                .map(node -> {
                    if (node instanceof CypherNodePattern) {
                        return NODE;
                    } else if (node instanceof CypherPatternPart) {
                        return PATH;
                    } else if (node instanceof CypherRelationshipDetail) {
                        CypherMaybeVariableLength maybeVariableLength =
                                ((CypherRelationshipDetail) node).getMaybeVariableLength();
                        return nonNull(maybeVariableLength) ? CypherList.of(RELATIONSHIP) : RELATIONSHIP;
                    }

                    return null;
                })
                .orElse(ANY);
    }
}
