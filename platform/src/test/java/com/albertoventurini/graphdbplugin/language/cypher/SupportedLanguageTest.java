/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher;

import com.albertoventurini.graphdbplugin.platform.SupportedLanguage;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SupportedLanguageTest {

    @Test
    public void languageSupported() {
        assertTrue(SupportedLanguage.isSupported(SupportedLanguage.CYPHER.getLanguageId()));
    }

    @Test
    public void languageUnsupported() {
        assertFalse(SupportedLanguage.isSupported("Java"));
    }

}
