/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements;

import java.util.regex.Pattern;

public interface CypherElementWithSignature {
    Pattern FULL_SIGNATURE_REGEXP = Pattern.compile("^(\\([^)]*\\)) :: \\(?([^)]*)\\)?$");

    InvokableInformation getInvokableInformation();

}
