/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.formatter;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes;
import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.albertoventurini.graphdbplugin.language.cypher.CypherLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CypherFormattingModelBuilder implements FormattingModelBuilder {

    @NotNull
    @Override
    public FormattingModel createModel(final @NotNull FormattingContext formattingContext) {
        final var element = formattingContext.getPsiElement();
        final var settings = formattingContext.getCodeStyleSettings();

        CypherBlock block = new CypherBlock(element.getNode(), Alignment.createAlignment(),
            Indent.getNoneIndent(), Wrap.createWrap(WrapType.NONE, false),
            settings,
            CypherFormattingModelBuilder.createSpacingBuilder(settings)
        );

        return FormattingModelProvider.createFormattingModelForPsiFile(element.getContainingFile(), block, settings);
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }

    static SpacingBuilder createSpacingBuilder(CodeStyleSettings settings) {
        return new SpacingBuilder(settings, CypherLanguage.INSTANCE)
                .before(CypherTypes.OP_COLON).none()
                .afterInside(CypherTypes.OP_COLON, CypherTypes.MAP_LITERAL).spaces(1)
                .after(CypherTypes.OP_COLON).none()

                .beforeInside(CypherTypes.PARENTHESIS_CLOSE, CypherTypes.FOREACH).lineBreakInCode()

                .withinPair(CypherTypes.PARENTHESIS_OPEN, CypherTypes.PARENTHESIS_CLOSE).none()
                .withinPair(CypherTypes.BRACKET_CURLYOPEN, CypherTypes.BRACKET_CURLYCLOSE).none()

                .before(CypherTypes.OP_COMMA).none()
                .after(CypherTypes.OP_COMMA).spaces(1)

                .between(CypherTypes.NODE_LABELS, CypherTypes.PROPERTIES).spaces(1)
                .between(CypherTypes.RELATIONSHIP_TYPES, CypherTypes.PROPERTIES).spaces(1)
                .between(CypherTypes.PARENTHESIZED_EXPRESSION, CypherTypes.PATTERN).none()

                .between(CypherTypes.NODE_LABEL, CypherTypes.NODE_LABEL).none()
                .between(CypherTypes.REL_TYPE, CypherTypes.REL_TYPE).none()

                .between(CypherTypes.NODE_PATTERN, CypherTypes.RELATIONSHIP_PATTERN).none()
                .between(CypherTypes.NODE_PATTERN, CypherTypes.PATTERN_ELEMENT_CHAIN).none()
                .between(CypherTypes.RELATIONSHIP_PATTERN, CypherTypes.NODE_PATTERN).none()
                .between(CypherTypes.PATTERN_ELEMENT_CHAIN, CypherTypes.NODE_PATTERN).none()
                .between(CypherTypes.VARIABLE, CypherTypes.NODE_LABELS).none()

                .between(CypherTypes.K_FOREACH, CypherTypes.PARENTHESIS_OPEN).spaces(1)
                .between(CypherTypes.EXPRESSION, CypherTypes.OP_PIPE).spaces(1)
                .between(CypherTypes.ID_IN_COLL, CypherTypes.OP_PIPE).spaces(1)
                .between(CypherTypes.FILTER_EXPRESSION, CypherTypes.OP_PIPE).spaces(1)

                .aroundInside(CypherTypes.PATTERN_ELEMENT, CypherTypes.PATTERN_PART).none()
                .aroundInside(CypherTypes.DASH, CypherTypes.RELATIONSHIP_PATTERN).none()
                .aroundInside(CypherTypes.LEFT_ARROW_HEAD, CypherTypes.RELATIONSHIP_PATTERN).none()
                .aroundInside(CypherTypes.RIGHT_ARROW_HEAD, CypherTypes.RELATIONSHIP_PATTERN).none()

                .afterInside(CypherTypes.NODE_LABEL, CypherTypes.HINT).none()
                .afterInside(CypherTypes.OP_MUL, CypherTypes.MAYBE_VARIABLE_LENGTH).none()

                .betweenInside(CypherTypes.OP_PLUS, CypherTypes.NUMBER_LITERAL, CypherTypes.UNARY_OPERATOR).none()
                .betweenInside(CypherTypes.OP_MINUS, CypherTypes.NUMBER_LITERAL, CypherTypes.UNARY_OPERATOR).none()
                .around(CypherTypes.OP_PLUS).spaces(1)
                .around(CypherTypes.OP_MINUS).spaces(1)
                .around(CypherTypes.OP_MUL).spaces(1)
                .around(CypherTypes.OP_DIVIDE).spaces(1)
                .around(CypherTypes.OP_MODULO).spaces(1)
                .around(CypherTypes.OP_POW).spaces(1)
                .around(CypherTypes.OP_EQUAL).spaces(1)
                .around(CypherTypes.OP_NOTEQUALS).spaces(1)
                .around(CypherTypes.OP_INVALIDNOTEQUALS).spaces(1)
                .around(CypherTypes.OP_LESSTHEN).spaces(1)
                .around(CypherTypes.OP_GREATHERTHEN).spaces(1)
                .around(CypherTypes.OP_LESSTHANEQUALS).spaces(1)
                .around(CypherTypes.OP_GREATERTHANEQUALS).spaces(1)

                .between(CypherTypes.STATEMENT_ITEM, CypherTypes.STATEMENT_ITEM).blankLines(1)
                .after(CypherTypes.PATTERN_COMPREHENSION).lineBreakInCode()
                .between(CypherTypes.K_RETURN, CypherTypes.RETURN_BODY).spaces(1)
                .between(CypherTypes.K_WITH, CypherTypes.RETURN_BODY).spaces(1)
                .between(CypherTypes.K_DISTINCT, CypherTypes.RETURN_BODY).spaces(1);
    }
}
