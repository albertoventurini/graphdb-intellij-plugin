/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.references;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherParenthesizedExpression;
import com.albertoventurini.graphdbplugin.language.cypher.references.types.CypherTypePropagator;
import com.albertoventurini.graphdbplugin.language.cypher.references.types.CypherTyped;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherType;

import java.util.Optional;

import static com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherSimpleType.ANY;

public interface CypherParenthesized extends CypherTyped {

    @Override
    default CypherType getType() {
        return Optional.of(this)
                .filter(CypherParenthesizedExpression.class::isInstance)
                .map(CypherParenthesizedExpression.class::cast)
                .map(CypherParenthesizedExpression::getExpression)
                .map(CypherTypePropagator::getType)
                .orElse(ANY);
    }

}
