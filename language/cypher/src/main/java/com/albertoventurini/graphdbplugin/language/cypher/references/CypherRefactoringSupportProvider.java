/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.references;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherLabelName;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherPropertyKeyName;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherRelTypeName;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherVariable;
import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Inline refactoring support.
 *
 * @author dmitry@vrublevsky.me
 */
public class CypherRefactoringSupportProvider extends RefactoringSupportProvider {

    @Override
    public boolean isMemberInplaceRenameAvailable(@NotNull PsiElement element, @Nullable PsiElement context) {
        return element instanceof CypherVariable
                || element instanceof CypherLabelName
                || element instanceof CypherRelTypeName
                || element instanceof CypherPropertyKeyName;
    }
}
