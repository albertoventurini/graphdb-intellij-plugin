/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.lang;

/**
 * Regular expressions for Cypher.
 */
public final class CypherRegexp {

    public static final String SYMBOLIC_NAME_REGEXP = "(([a-zA-Z_][a-zA-Z_$0-9]*)|(`[^`]+`))";

    private CypherRegexp() {
    }
}
