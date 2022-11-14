/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.platform;

import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

public enum SupportedLanguage {
    CYPHER("Cypher");

    private final String languageId;

    SupportedLanguage(String languageId) {
        this.languageId = languageId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public static boolean isSupported(String languageId) {
        checkNotNull(languageId, "'languageId' is undefined");
        return Stream.of(values())
                .anyMatch(language -> language.getLanguageId().equals(languageId));
    }

}
