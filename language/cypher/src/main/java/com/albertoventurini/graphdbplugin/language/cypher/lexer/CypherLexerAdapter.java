/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.lexer;

import com.intellij.lexer.FlexAdapter;

/**
 * TODO: Description
 *
 * @author dmitry@vrublesvky.me
 */
public class CypherLexerAdapter extends FlexAdapter {

    public CypherLexerAdapter() {
        super(new _CypherLexer());
    }
}
