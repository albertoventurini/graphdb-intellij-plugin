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
public class CypherTokenType extends IElementType {

    private final String originalName;
    private final String representation;

    public CypherTokenType(@NotNull @NonNls String debugName) {
        super(debugName, CypherLanguage.INSTANCE);

        originalName = debugName;
        if (originalName.startsWith("K_")) {
            representation = originalName.substring(2);
        } else if (originalName.startsWith("l_")) {
            representation = originalName.substring(2);
        } else {
            representation = originalName;
        }
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getRepresentation() {
        return representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
