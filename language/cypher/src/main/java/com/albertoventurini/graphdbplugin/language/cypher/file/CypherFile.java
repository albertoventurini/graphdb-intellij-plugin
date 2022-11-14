/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.file;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.albertoventurini.graphdbplugin.language.cypher.CypherLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

/**
 * Describes Cypher file.
 *
 * @author dmitry@vrublevsky.me
 */
public class CypherFile extends PsiFileBase {

    public CypherFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, CypherLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return CypherFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Cypher file: " + super.toString();
    }

    @Nullable
    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}
