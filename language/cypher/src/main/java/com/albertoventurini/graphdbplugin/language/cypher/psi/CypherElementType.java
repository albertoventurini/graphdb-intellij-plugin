/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.psi;

import com.intellij.psi.tree.IElementType;
import com.albertoventurini.graphdbplugin.language.cypher.CypherLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * TODO: Description
 *
 * @author dmitry@vrublesvky.me
 */
public class CypherElementType extends IElementType {

    public CypherElementType(@NotNull @NonNls String debugName) {
        super(debugName, CypherLanguage.INSTANCE);
    }
}
