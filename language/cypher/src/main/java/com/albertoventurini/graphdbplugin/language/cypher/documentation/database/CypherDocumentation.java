/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.documentation.database;

import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherBuiltInFunctions;

public abstract class CypherDocumentation {

    public static final DocumentationStorage BUILT_IN_FUNCTIONS =
            new DocumentationStorage("built-in/functions", CypherBuiltInFunctions.FUNCTION_NAMES);
}
