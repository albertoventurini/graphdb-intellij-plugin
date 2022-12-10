/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.lang;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes;
import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * TODO: Description
 *
 * @author dmitry@vrublevsky.me
 */
public class CypherBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] PAIRS = {
            new BracePair(CypherTypes.PARENTHESIS_OPEN, CypherTypes.PARENTHESIS_CLOSE, true),
            new BracePair(CypherTypes.BRACKET_SQUAREOPEN, CypherTypes.BRACKET_SQUARECLOSE, true),
            // todo: curly braces are a bit buggy?
            new BracePair(CypherTypes.BRACKET_CURLYOPEN, CypherTypes.BRACKET_CURLYCLOSE, true),
    };

    @Override
    public @NotNull BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
