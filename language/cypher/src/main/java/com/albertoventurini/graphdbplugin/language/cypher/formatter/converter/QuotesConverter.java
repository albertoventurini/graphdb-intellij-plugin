/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.formatter.converter;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes;
import com.google.common.base.CharMatcher;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiElement;
import com.albertoventurini.graphdbplugin.language.cypher.formatter.CypherPreFormatter;
import org.jetbrains.annotations.NotNull;

public class QuotesConverter extends AbstractCypherConverter {

    public QuotesConverter(CypherPreFormatter.FormatterTask formatterTask, @NotNull Document document) {
        super(formatterTask, document);
    }

    @Override
    protected String convert(PsiElement element) {
        if (element.getNode().getElementType() == CypherTypes.STRING_LITERAL) {
            String text = element.getText().substring(1, element.getTextLength() - 1);
            int singleCount = CharMatcher.is('\'').countIn(text);
            int doubleCount = CharMatcher.is('"').countIn(text);

            if (singleCount <= doubleCount) {
                text = text
                        .replace("'", "\\'")
                        .replace("\\\"", "\"");
                return "'" + text + "'";
            } else {
                text = text
                        .replace("\"", "\\\"")
                        .replace("\\'", "'");
                return "\"" + text + "\"";
            }
        }

        return null;
    }

}
