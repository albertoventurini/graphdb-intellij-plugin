/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher;

import com.albertoventurini.graphdbplugin.language.cypher.file.CypherFile;
import com.albertoventurini.graphdbplugin.language.cypher.lexer.CypherLexerAdapter;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTokenType;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.*;

import org.jetbrains.annotations.NotNull;

/**
 * Definer lexer and parser.
 *
 * @author dmitry@vrublesvky.me
 */
public class CypherParserDefinition implements ParserDefinition {
    public static final IElementType LINE_COMMENT = new CypherTokenType("LINE_COMMENT");
    public static final IElementType BLOCK_COMMENT = new CypherTokenType("BLOCK_COMMENT");

    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create(LINE_COMMENT, BLOCK_COMMENT);
    public static final TokenSet STRINGS = TokenSet.create(CypherTypes.L_STRING);

    public static final IFileElementType FILE =
            new IFileElementType(Language.findInstance(CypherLanguage.class));

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new CypherLexerAdapter();
    }

    @Override
    public PsiParser createParser(Project project) {
        return new CypherParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return STRINGS;
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return CypherTypes.Factory.createElement(node);
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new CypherFile(viewProvider);
    }

    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }
}
