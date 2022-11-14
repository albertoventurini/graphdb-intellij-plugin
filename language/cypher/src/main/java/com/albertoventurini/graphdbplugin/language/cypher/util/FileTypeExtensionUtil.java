/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.util;

import java.util.Arrays;
import java.util.List;

public class FileTypeExtensionUtil {

    public static final List<String> EXTENSIONS = Arrays.asList("cyp", "cql", "cypher");

    public static boolean isCypherFileTypeExtension(String extension) {
        return extension != null && EXTENSIONS.contains(extension);
    }

}
