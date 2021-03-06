package com.github.syuchan1005.gitprefix.grammar;

import com.github.syuchan1005.gitprefix.filetype.PrefixResourceLanguage;
import com.github.syuchan1005.gitprefix.grammar.parser.PrefixResourceParser;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;


import static com.github.syuchan1005.gitprefix.grammar.psi.PrefixResourceTypes.*;

public class PrefixResourceParserDefinition implements ParserDefinition {
	public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
	public static final TokenSet COMMENTS = TokenSet.create(LINE_COMMENT, BLOCK_COMMENT);
	public static final TokenSet LITERALS = TokenSet.create(VALUE);

	public static final IFileElementType FILE = new IFileElementType(PrefixResourceLanguage.INSTANCE);

	@NotNull
	@Override
	public Lexer createLexer(Project project) {
		return new PrefixResourceLexerAdapter();
	}

	@Override
	public PsiParser createParser(Project project) {
		return new PrefixResourceParser();
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
		return LITERALS;
	}

	@NotNull
	@Override
	public PsiElement createElement(ASTNode astNode) {
		return Factory.createElement(astNode);
	}

	@Override
	public PsiFile createFile(FileViewProvider fileViewProvider) {
		return new PrefixResourceFile(fileViewProvider);
	}
}
