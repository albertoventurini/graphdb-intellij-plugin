/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.file;

import com.albertoventurini.graphdbplugin.language.cypher.CypherIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.albertoventurini.graphdbplugin.language.cypher.CypherLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

/**
 * Describe Cypher file basic metadata.
 *
 * @author dmitry@vrublevsky.me
 */
public final class CypherFileType extends LanguageFileType {

    public static final CypherFileType INSTANCE = new CypherFileType();

    private CypherFileType() {
        super(CypherLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Cypher";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Cypher query language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "cyp";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return CypherIcons.FILE;
    }
}
