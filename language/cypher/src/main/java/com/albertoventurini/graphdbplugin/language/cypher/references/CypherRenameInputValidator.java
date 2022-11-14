/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.references;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.rename.RenameInputValidator;
import com.intellij.util.ProcessingContext;
import com.albertoventurini.graphdbplugin.language.cypher.CypherLanguage;
import com.albertoventurini.graphdbplugin.language.cypher.file.CypherFile;
import com.albertoventurini.graphdbplugin.language.cypher.lang.CypherRegexp;

/**
 * Validates identifier renaming.
 *
 * @author dmitry@vrublevsky.me
 */
public class CypherRenameInputValidator implements RenameInputValidator {

    @Override
    public ElementPattern<? extends PsiElement> getPattern() {
        return PlatformPatterns.psiElement().withLanguage(CypherLanguage.INSTANCE);
    }

    @Override
    public boolean isInputValid(String newName, PsiElement element, ProcessingContext context) {
        if (element instanceof CypherFile) {
            // Cypher file can have any name.
            return true;
        }
        return newName.matches(CypherRegexp.SYMBOLIC_NAME_REGEXP);
    }
}
