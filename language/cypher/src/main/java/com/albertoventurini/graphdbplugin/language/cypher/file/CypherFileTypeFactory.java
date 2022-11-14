/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.file;

import com.albertoventurini.graphdbplugin.language.cypher.util.FileTypeExtensionUtil;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Associate Cypher file with extension.
 *
 * @author dmitry@vrublevsky.me
 */
public class CypherFileTypeFactory extends FileTypeFactory {

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(CypherFileType.INSTANCE, String.join(";", FileTypeExtensionUtil.EXTENSIONS));
    }
}
