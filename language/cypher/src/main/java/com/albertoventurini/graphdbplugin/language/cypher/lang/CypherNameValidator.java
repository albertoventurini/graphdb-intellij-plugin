/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.lang;

import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherKeywords;
import com.intellij.lang.refactoring.NamesValidator;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class CypherNameValidator implements NamesValidator {

    @Override
    public boolean isKeyword(@NotNull String name, Project project) {
        return CypherKeywords.KEYWORDS.contains(name.toLowerCase());
    }

    @Override
    public boolean isIdentifier(@NotNull String name, Project project) {
        return !isKeyword(name, project) && name.matches(CypherRegexp.SYMBOLIC_NAME_REGEXP);
    }
}
