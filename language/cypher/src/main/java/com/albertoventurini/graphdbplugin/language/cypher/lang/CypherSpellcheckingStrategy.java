/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.lang;

import com.intellij.psi.PsiElement;
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy;
import com.albertoventurini.graphdbplugin.language.cypher.CypherLanguage;
import org.jetbrains.annotations.NotNull;

public class CypherSpellcheckingStrategy extends SpellcheckingStrategy {

    @Override
    public boolean isMyContext(@NotNull PsiElement element) {
        return CypherLanguage.INSTANCE.is(element.getLanguage());
    }
}
