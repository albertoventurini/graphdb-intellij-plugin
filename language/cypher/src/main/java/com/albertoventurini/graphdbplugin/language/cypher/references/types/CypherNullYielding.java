/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.references.types;

import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherType;

import static com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherSimpleType.NULL;

public interface CypherNullYielding extends CypherTyped {

    @Override
    default CypherType getType() {
        return NULL;
    }

}
