// Generated from C:/Users/Ruke/IntelliJ/vrJASS\vrjass.g4 by ANTLR 4.5.1
package com.ruke.vrjassc.vrjassc.antlr4;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class vrjassParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ABSTRACT=1, BREAK=2, WHILE=3, ENDWHILE=4, END=5, CAST=6, LIBRARY=7, ENDLIBRARY=8, 
		INITIALIZER=9, REQUIRES=10, THIS=11, THISTYPE=12, SUPER=13, DOT=14, PRIVATE=15, 
		PUBLIC=16, AND=17, OR=18, NULL=19, NOT=20, ARRAY=21, LOCAL=22, SET=23, 
		EXITWHEN=24, CALL=25, GLOBALS=26, ENDGLOBALS=27, EXTENDS=28, IMPLEMENTS=29, 
		TYPE=30, NATIVE=31, CONSTANT=32, STATIC=33, STRUCT=34, ENDSTRUCT=35, METHOD=36, 
		ENDMETHOD=37, INTERFACE=38, ENDINTERFACE=39, FUNCTION=40, ENDFUNCTION=41, 
		TAKES=42, RETURNS=43, RETURN=44, IF=45, THEN=46, ELSEIF=47, ELSE=48, ENDIF=49, 
		LOOP=50, ENDLOOP=51, TRUE=52, FALSE=53, NOTHING=54, PAREN_LEFT=55, PAREN_RIGHT=56, 
		BRACKET_LEFT=57, BRACKET_RIGHT=58, COMMA=59, DIV=60, TIMES=61, MINUS=62, 
		PLUS=63, EQ=64, EQEQ=65, NOT_EQ=66, LESS=67, LESS_EQ=68, GREATER=69, GREATER_EQ=70, 
		NL=71, ID=72, STRING=73, REAL=74, INT=75, WS=76, COMMENT=77, LINE_COMMENT=78;
	public static final int
		RULE_init = 0, RULE_topDeclaration = 1, RULE_validType = 2, RULE_validName = 3, 
		RULE_variableDeclaration = 4, RULE_expression = 5, RULE_typeDefinition = 6, 
		RULE_nativeDefinition = 7, RULE_globalVariableStatement = 8, RULE_globalStatements = 9, 
		RULE_globalDefinition = 10, RULE_libraryRequirements = 11, RULE_libraryStatement = 12, 
		RULE_libraryDefinition = 13, RULE_interfaceDefinition = 14, RULE_propertyStatement = 15, 
		RULE_structStatement = 16, RULE_implementsList = 17, RULE_structDefinition = 18, 
		RULE_returnType = 19, RULE_parameter = 20, RULE_parameters = 21, RULE_arguments = 22, 
		RULE_functionSignature = 23, RULE_functionDefinitionExpression = 24, RULE_functionDefinition = 25, 
		RULE_statement = 26, RULE_localVariableStatement = 27, RULE_assignmentStatement = 28, 
		RULE_functionStatement = 29, RULE_breakStatement = 30, RULE_exitWhenStatement = 31, 
		RULE_loopStatement = 32, RULE_whileLoopStatement = 33, RULE_ifStatement = 34, 
		RULE_elseIfStatement = 35, RULE_elseStatement = 36, RULE_returnStatement = 37;
	public static final String[] ruleNames = {
		"init", "topDeclaration", "validType", "validName", "variableDeclaration", 
		"expression", "typeDefinition", "nativeDefinition", "globalVariableStatement", 
		"globalStatements", "globalDefinition", "libraryRequirements", "libraryStatement", 
		"libraryDefinition", "interfaceDefinition", "propertyStatement", "structStatement", 
		"implementsList", "structDefinition", "returnType", "parameter", "parameters", 
		"arguments", "functionSignature", "functionDefinitionExpression", "functionDefinition", 
		"statement", "localVariableStatement", "assignmentStatement", "functionStatement", 
		"breakStatement", "exitWhenStatement", "loopStatement", "whileLoopStatement", 
		"ifStatement", "elseIfStatement", "elseStatement", "returnStatement"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'abstract'", "'break'", "'while'", "'endwhile'", "'end'", "'cast'", 
		"'library'", "'endlibrary'", "'initializer'", "'requires'", "'this'", 
		"'thistype'", "'super'", "'.'", "'private'", "'public'", "'and'", "'or'", 
		"'null'", "'not'", "'array'", "'local'", "'set'", "'exitwhen'", "'call'", 
		"'globals'", "'endglobals'", "'extends'", "'implements'", "'type'", "'native'", 
		"'constant'", "'static'", "'struct'", "'endstruct'", "'method'", "'endmethod'", 
		"'interface'", "'endinterface'", "'function'", "'endfunction'", "'takes'", 
		"'returns'", "'return'", "'if'", "'then'", "'elseif'", "'else'", "'endif'", 
		"'loop'", "'endloop'", "'true'", "'false'", "'nothing'", "'('", "')'", 
		"'['", "']'", "','", "'/'", "'*'", "'-'", "'+'", "'='", "'=='", "'!='", 
		"'<'", "'<='", "'>'", "'>='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "ABSTRACT", "BREAK", "WHILE", "ENDWHILE", "END", "CAST", "LIBRARY", 
		"ENDLIBRARY", "INITIALIZER", "REQUIRES", "THIS", "THISTYPE", "SUPER", 
		"DOT", "PRIVATE", "PUBLIC", "AND", "OR", "NULL", "NOT", "ARRAY", "LOCAL", 
		"SET", "EXITWHEN", "CALL", "GLOBALS", "ENDGLOBALS", "EXTENDS", "IMPLEMENTS", 
		"TYPE", "NATIVE", "CONSTANT", "STATIC", "STRUCT", "ENDSTRUCT", "METHOD", 
		"ENDMETHOD", "INTERFACE", "ENDINTERFACE", "FUNCTION", "ENDFUNCTION", "TAKES", 
		"RETURNS", "RETURN", "IF", "THEN", "ELSEIF", "ELSE", "ENDIF", "LOOP", 
		"ENDLOOP", "TRUE", "FALSE", "NOTHING", "PAREN_LEFT", "PAREN_RIGHT", "BRACKET_LEFT", 
		"BRACKET_RIGHT", "COMMA", "DIV", "TIMES", "MINUS", "PLUS", "EQ", "EQEQ", 
		"NOT_EQ", "LESS", "LESS_EQ", "GREATER", "GREATER_EQ", "NL", "ID", "STRING", 
		"REAL", "INT", "WS", "COMMENT", "LINE_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "vrjass.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public vrjassParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class InitContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(vrjassParser.EOF, 0); }
		public List<TopDeclarationContext> topDeclaration() {
			return getRuleContexts(TopDeclarationContext.class);
		}
		public TopDeclarationContext topDeclaration(int i) {
			return getRuleContext(TopDeclarationContext.class,i);
		}
		public InitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_init; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InitContext init() throws RecognitionException {
		InitContext _localctx = new InitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_init);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << LIBRARY) | (1L << PRIVATE) | (1L << PUBLIC) | (1L << GLOBALS) | (1L << TYPE) | (1L << NATIVE) | (1L << CONSTANT) | (1L << STATIC) | (1L << STRUCT) | (1L << METHOD) | (1L << INTERFACE) | (1L << FUNCTION))) != 0) || _la==NL) {
				{
				{
				setState(76);
				topDeclaration();
				}
				}
				setState(81);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(82);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TopDeclarationContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public TypeDefinitionContext typeDefinition() {
			return getRuleContext(TypeDefinitionContext.class,0);
		}
		public NativeDefinitionContext nativeDefinition() {
			return getRuleContext(NativeDefinitionContext.class,0);
		}
		public GlobalDefinitionContext globalDefinition() {
			return getRuleContext(GlobalDefinitionContext.class,0);
		}
		public LibraryDefinitionContext libraryDefinition() {
			return getRuleContext(LibraryDefinitionContext.class,0);
		}
		public InterfaceDefinitionContext interfaceDefinition() {
			return getRuleContext(InterfaceDefinitionContext.class,0);
		}
		public StructDefinitionContext structDefinition() {
			return getRuleContext(StructDefinitionContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public TopDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_topDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitTopDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TopDeclarationContext topDeclaration() throws RecognitionException {
		TopDeclarationContext _localctx = new TopDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_topDeclaration);
		try {
			setState(92);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				match(NL);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				typeDefinition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(86);
				nativeDefinition();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(87);
				globalDefinition();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(88);
				libraryDefinition();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(89);
				interfaceDefinition();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(90);
				structDefinition();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(91);
				functionDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValidTypeContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ValidTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_validType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitValidType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValidTypeContext validType() throws RecognitionException {
		ValidTypeContext _localctx = new ValidTypeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_validType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValidNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(vrjassParser.ID, 0); }
		public TerminalNode END() { return getToken(vrjassParser.END, 0); }
		public ValidNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_validName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitValidName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValidNameContext validName() throws RecognitionException {
		ValidNameContext _localctx = new ValidNameContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_validName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			_la = _input.LA(1);
			if ( !(_la==END || _la==ID) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public ValidTypeContext type;
		public ValidNameContext name;
		public ExpressionContext value;
		public ValidTypeContext validType() {
			return getRuleContext(ValidTypeContext.class,0);
		}
		public ValidNameContext validName() {
			return getRuleContext(ValidNameContext.class,0);
		}
		public TerminalNode ARRAY() { return getToken(vrjassParser.ARRAY, 0); }
		public TerminalNode EQ() { return getToken(vrjassParser.EQ, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			((VariableDeclarationContext)_localctx).type = validType();
			setState(100);
			_la = _input.LA(1);
			if (_la==ARRAY) {
				{
				setState(99);
				match(ARRAY);
				}
			}

			setState(102);
			((VariableDeclarationContext)_localctx).name = validName();
			setState(105);
			_la = _input.LA(1);
			if (_la==EQ) {
				{
				setState(103);
				match(EQ);
				setState(104);
				((VariableDeclarationContext)_localctx).value = expression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CastContext extends ExpressionContext {
		public ExpressionContext original;
		public ExpressionContext casted;
		public TerminalNode CAST() { return getToken(vrjassParser.CAST, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public CastContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitCast(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NullContext extends ExpressionContext {
		public TerminalNode NULL() { return getToken(vrjassParser.NULL, 0); }
		public NullContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitNull(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ChainExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode DOT() { return getToken(vrjassParser.DOT, 0); }
		public ChainExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitChainExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicalContext extends ExpressionContext {
		public ExpressionContext left;
		public Token operator;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode OR() { return getToken(vrjassParser.OR, 0); }
		public TerminalNode AND() { return getToken(vrjassParser.AND, 0); }
		public LogicalContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitLogical(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VariableExpressionContext extends ExpressionContext {
		public ExpressionContext index;
		public ValidNameContext validName() {
			return getRuleContext(ValidNameContext.class,0);
		}
		public TerminalNode BRACKET_LEFT() { return getToken(vrjassParser.BRACKET_LEFT, 0); }
		public TerminalNode BRACKET_RIGHT() { return getToken(vrjassParser.BRACKET_RIGHT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitVariableExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringContext extends ExpressionContext {
		public TerminalNode STRING() { return getToken(vrjassParser.STRING, 0); }
		public StringContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CodeContext extends ExpressionContext {
		public TerminalNode FUNCTION() { return getToken(vrjassParser.FUNCTION, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CodeContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitCode(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntegerContext extends ExpressionContext {
		public TerminalNode INT() { return getToken(vrjassParser.INT, 0); }
		public IntegerContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitInteger(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DivContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode DIV() { return getToken(vrjassParser.DIV, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public DivContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AnonymousExpressionContext extends ExpressionContext {
		public FunctionDefinitionExpressionContext functionDefinitionExpression() {
			return getRuleContext(FunctionDefinitionExpressionContext.class,0);
		}
		public AnonymousExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitAnonymousExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotContext extends ExpressionContext {
		public TerminalNode NOT() { return getToken(vrjassParser.NOT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NotContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitNot(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenthesisContext extends ExpressionContext {
		public TerminalNode PAREN_LEFT() { return getToken(vrjassParser.PAREN_LEFT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode PAREN_RIGHT() { return getToken(vrjassParser.PAREN_RIGHT, 0); }
		public ParenthesisContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitParenthesis(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionExpressionContext extends ExpressionContext {
		public ValidNameContext validName() {
			return getRuleContext(ValidNameContext.class,0);
		}
		public TerminalNode PAREN_LEFT() { return getToken(vrjassParser.PAREN_LEFT, 0); }
		public TerminalNode PAREN_RIGHT() { return getToken(vrjassParser.PAREN_RIGHT, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public FunctionExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitFunctionExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegativeContext extends ExpressionContext {
		public TerminalNode MINUS() { return getToken(vrjassParser.MINUS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NegativeContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitNegative(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode TIMES() { return getToken(vrjassParser.TIMES, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MultContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitMult(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ComparisonContext extends ExpressionContext {
		public ExpressionContext left;
		public Token operator;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode EQEQ() { return getToken(vrjassParser.EQEQ, 0); }
		public TerminalNode NOT_EQ() { return getToken(vrjassParser.NOT_EQ, 0); }
		public TerminalNode GREATER() { return getToken(vrjassParser.GREATER, 0); }
		public TerminalNode GREATER_EQ() { return getToken(vrjassParser.GREATER_EQ, 0); }
		public TerminalNode LESS() { return getToken(vrjassParser.LESS, 0); }
		public TerminalNode LESS_EQ() { return getToken(vrjassParser.LESS_EQ, 0); }
		public ComparisonContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitComparison(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RealContext extends ExpressionContext {
		public TerminalNode REAL() { return getToken(vrjassParser.REAL, 0); }
		public RealContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitReal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ThisContext extends ExpressionContext {
		public TerminalNode THIS() { return getToken(vrjassParser.THIS, 0); }
		public ThisContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitThis(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BooleanContext extends ExpressionContext {
		public TerminalNode TRUE() { return getToken(vrjassParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(vrjassParser.FALSE, 0); }
		public BooleanContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitBoolean(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PlusContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode PLUS() { return getToken(vrjassParser.PLUS, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public PlusContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitPlus(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MinusContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode MINUS() { return getToken(vrjassParser.MINUS, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MinusContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitMinus(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				_localctx = new NegativeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(108);
				match(MINUS);
				setState(109);
				expression(17);
				}
				break;
			case 2:
				{
				_localctx = new NotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(110);
				match(NOT);
				setState(111);
				expression(16);
				}
				break;
			case 3:
				{
				_localctx = new CodeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(112);
				match(FUNCTION);
				setState(113);
				expression(8);
				}
				break;
			case 4:
				{
				_localctx = new AnonymousExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(114);
				functionDefinitionExpression();
				}
				break;
			case 5:
				{
				_localctx = new IntegerContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(115);
				match(INT);
				}
				break;
			case 6:
				{
				_localctx = new RealContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(116);
				match(REAL);
				}
				break;
			case 7:
				{
				_localctx = new StringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(117);
				match(STRING);
				}
				break;
			case 8:
				{
				_localctx = new BooleanContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(118);
				_la = _input.LA(1);
				if ( !(_la==TRUE || _la==FALSE) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 9:
				{
				_localctx = new NullContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(119);
				match(NULL);
				}
				break;
			case 10:
				{
				_localctx = new ThisContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(120);
				match(THIS);
				}
				break;
			case 11:
				{
				_localctx = new VariableExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(121);
				validName();
				setState(126);
				switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
				case 1:
					{
					setState(122);
					match(BRACKET_LEFT);
					setState(123);
					((VariableExpressionContext)_localctx).index = expression(0);
					setState(124);
					match(BRACKET_RIGHT);
					}
					break;
				}
				}
				break;
			case 12:
				{
				_localctx = new FunctionExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(128);
				validName();
				setState(129);
				match(PAREN_LEFT);
				setState(131);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << END) | (1L << THIS) | (1L << PRIVATE) | (1L << PUBLIC) | (1L << NULL) | (1L << NOT) | (1L << NATIVE) | (1L << CONSTANT) | (1L << STATIC) | (1L << METHOD) | (1L << FUNCTION) | (1L << TRUE) | (1L << FALSE) | (1L << PAREN_LEFT) | (1L << MINUS))) != 0) || ((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (ID - 72)) | (1L << (STRING - 72)) | (1L << (REAL - 72)) | (1L << (INT - 72)))) != 0)) {
					{
					setState(130);
					arguments();
					}
				}

				setState(133);
				match(PAREN_RIGHT);
				}
				break;
			case 13:
				{
				_localctx = new ParenthesisContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(135);
				match(PAREN_LEFT);
				setState(136);
				expression(0);
				setState(137);
				match(PAREN_RIGHT);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(167);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(165);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new ChainExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(141);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(142);
						match(DOT);
						setState(143);
						expression(11);
						}
						break;
					case 2:
						{
						_localctx = new CastContext(new ExpressionContext(_parentctx, _parentState));
						((CastContext)_localctx).original = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(144);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(145);
						match(CAST);
						setState(146);
						((CastContext)_localctx).casted = expression(10);
						}
						break;
					case 3:
						{
						_localctx = new DivContext(new ExpressionContext(_parentctx, _parentState));
						((DivContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(147);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(148);
						match(DIV);
						setState(149);
						((DivContext)_localctx).right = expression(8);
						}
						break;
					case 4:
						{
						_localctx = new MultContext(new ExpressionContext(_parentctx, _parentState));
						((MultContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(150);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(151);
						match(TIMES);
						setState(152);
						((MultContext)_localctx).right = expression(7);
						}
						break;
					case 5:
						{
						_localctx = new MinusContext(new ExpressionContext(_parentctx, _parentState));
						((MinusContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(153);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(154);
						match(MINUS);
						setState(155);
						((MinusContext)_localctx).right = expression(6);
						}
						break;
					case 6:
						{
						_localctx = new PlusContext(new ExpressionContext(_parentctx, _parentState));
						((PlusContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(156);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(157);
						match(PLUS);
						setState(158);
						((PlusContext)_localctx).right = expression(5);
						}
						break;
					case 7:
						{
						_localctx = new ComparisonContext(new ExpressionContext(_parentctx, _parentState));
						((ComparisonContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(159);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(160);
						((ComparisonContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (EQEQ - 65)) | (1L << (NOT_EQ - 65)) | (1L << (LESS - 65)) | (1L << (LESS_EQ - 65)) | (1L << (GREATER - 65)) | (1L << (GREATER_EQ - 65)))) != 0)) ) {
							((ComparisonContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(161);
						((ComparisonContext)_localctx).right = expression(4);
						}
						break;
					case 8:
						{
						_localctx = new LogicalContext(new ExpressionContext(_parentctx, _parentState));
						((LogicalContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(162);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(163);
						((LogicalContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==AND || _la==OR) ) {
							((LogicalContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(164);
						((LogicalContext)_localctx).right = expression(3);
						}
						break;
					}
					} 
				}
				setState(169);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TypeDefinitionContext extends ParserRuleContext {
		public TerminalNode TYPE() { return getToken(vrjassParser.TYPE, 0); }
		public ValidNameContext validName() {
			return getRuleContext(ValidNameContext.class,0);
		}
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public TerminalNode EXTENDS() { return getToken(vrjassParser.EXTENDS, 0); }
		public ValidTypeContext validType() {
			return getRuleContext(ValidTypeContext.class,0);
		}
		public TypeDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitTypeDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDefinitionContext typeDefinition() throws RecognitionException {
		TypeDefinitionContext _localctx = new TypeDefinitionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_typeDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(TYPE);
			setState(171);
			validName();
			setState(174);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(172);
				match(EXTENDS);
				setState(173);
				validType();
				}
			}

			setState(176);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NativeDefinitionContext extends ParserRuleContext {
		public FunctionSignatureContext functionSignature() {
			return getRuleContext(FunctionSignatureContext.class,0);
		}
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public NativeDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nativeDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitNativeDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NativeDefinitionContext nativeDefinition() throws RecognitionException {
		NativeDefinitionContext _localctx = new NativeDefinitionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_nativeDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			functionSignature();
			setState(179);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GlobalVariableStatementContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public TerminalNode CONSTANT() { return getToken(vrjassParser.CONSTANT, 0); }
		public TerminalNode PRIVATE() { return getToken(vrjassParser.PRIVATE, 0); }
		public TerminalNode PUBLIC() { return getToken(vrjassParser.PUBLIC, 0); }
		public GlobalVariableStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalVariableStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitGlobalVariableStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalVariableStatementContext globalVariableStatement() throws RecognitionException {
		GlobalVariableStatementContext _localctx = new GlobalVariableStatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_globalVariableStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(181);
				_la = _input.LA(1);
				if ( !(_la==PRIVATE || _la==PUBLIC) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			}
			setState(185);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(184);
				match(CONSTANT);
				}
				break;
			}
			setState(187);
			variableDeclaration();
			setState(188);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GlobalStatementsContext extends ParserRuleContext {
		public List<TerminalNode> NL() { return getTokens(vrjassParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(vrjassParser.NL, i);
		}
		public List<GlobalVariableStatementContext> globalVariableStatement() {
			return getRuleContexts(GlobalVariableStatementContext.class);
		}
		public GlobalVariableStatementContext globalVariableStatement(int i) {
			return getRuleContext(GlobalVariableStatementContext.class,i);
		}
		public GlobalStatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalStatements; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitGlobalStatements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalStatementsContext globalStatements() throws RecognitionException {
		GlobalStatementsContext _localctx = new GlobalStatementsContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_globalStatements);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(192);
					switch (_input.LA(1)) {
					case NL:
						{
						setState(190);
						match(NL);
						}
						break;
					case ABSTRACT:
					case END:
					case THIS:
					case PRIVATE:
					case PUBLIC:
					case NULL:
					case NOT:
					case NATIVE:
					case CONSTANT:
					case STATIC:
					case METHOD:
					case FUNCTION:
					case TRUE:
					case FALSE:
					case PAREN_LEFT:
					case MINUS:
					case ID:
					case STRING:
					case REAL:
					case INT:
						{
						setState(191);
						globalVariableStatement();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					} 
				}
				setState(196);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GlobalDefinitionContext extends ParserRuleContext {
		public TerminalNode GLOBALS() { return getToken(vrjassParser.GLOBALS, 0); }
		public List<TerminalNode> NL() { return getTokens(vrjassParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(vrjassParser.NL, i);
		}
		public GlobalStatementsContext globalStatements() {
			return getRuleContext(GlobalStatementsContext.class,0);
		}
		public TerminalNode ENDGLOBALS() { return getToken(vrjassParser.ENDGLOBALS, 0); }
		public TerminalNode END() { return getToken(vrjassParser.END, 0); }
		public GlobalDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitGlobalDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalDefinitionContext globalDefinition() throws RecognitionException {
		GlobalDefinitionContext _localctx = new GlobalDefinitionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_globalDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			match(GLOBALS);
			setState(198);
			match(NL);
			setState(199);
			globalStatements();
			setState(200);
			_la = _input.LA(1);
			if ( !(_la==END || _la==ENDGLOBALS) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(201);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LibraryRequirementsContext extends ParserRuleContext {
		public List<ValidNameContext> validName() {
			return getRuleContexts(ValidNameContext.class);
		}
		public ValidNameContext validName(int i) {
			return getRuleContext(ValidNameContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(vrjassParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(vrjassParser.COMMA, i);
		}
		public LibraryRequirementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_libraryRequirements; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitLibraryRequirements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LibraryRequirementsContext libraryRequirements() throws RecognitionException {
		LibraryRequirementsContext _localctx = new LibraryRequirementsContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_libraryRequirements);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			validName();
			setState(208);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(204);
				match(COMMA);
				setState(205);
				validName();
				}
				}
				setState(210);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LibraryStatementContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public GlobalDefinitionContext globalDefinition() {
			return getRuleContext(GlobalDefinitionContext.class,0);
		}
		public InterfaceDefinitionContext interfaceDefinition() {
			return getRuleContext(InterfaceDefinitionContext.class,0);
		}
		public StructDefinitionContext structDefinition() {
			return getRuleContext(StructDefinitionContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public LibraryStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_libraryStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitLibraryStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LibraryStatementContext libraryStatement() throws RecognitionException {
		LibraryStatementContext _localctx = new LibraryStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_libraryStatement);
		try {
			setState(216);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(211);
				match(NL);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(212);
				globalDefinition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(213);
				interfaceDefinition();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(214);
				structDefinition();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(215);
				functionDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LibraryDefinitionContext extends ParserRuleContext {
		public ValidNameContext name;
		public ValidNameContext libInit;
		public TerminalNode LIBRARY() { return getToken(vrjassParser.LIBRARY, 0); }
		public List<TerminalNode> NL() { return getTokens(vrjassParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(vrjassParser.NL, i);
		}
		public List<ValidNameContext> validName() {
			return getRuleContexts(ValidNameContext.class);
		}
		public ValidNameContext validName(int i) {
			return getRuleContext(ValidNameContext.class,i);
		}
		public TerminalNode ENDLIBRARY() { return getToken(vrjassParser.ENDLIBRARY, 0); }
		public TerminalNode END() { return getToken(vrjassParser.END, 0); }
		public TerminalNode INITIALIZER() { return getToken(vrjassParser.INITIALIZER, 0); }
		public TerminalNode REQUIRES() { return getToken(vrjassParser.REQUIRES, 0); }
		public LibraryRequirementsContext libraryRequirements() {
			return getRuleContext(LibraryRequirementsContext.class,0);
		}
		public List<LibraryStatementContext> libraryStatement() {
			return getRuleContexts(LibraryStatementContext.class);
		}
		public LibraryStatementContext libraryStatement(int i) {
			return getRuleContext(LibraryStatementContext.class,i);
		}
		public LibraryDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_libraryDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitLibraryDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LibraryDefinitionContext libraryDefinition() throws RecognitionException {
		LibraryDefinitionContext _localctx = new LibraryDefinitionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_libraryDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			match(LIBRARY);
			setState(219);
			((LibraryDefinitionContext)_localctx).name = validName();
			setState(222);
			_la = _input.LA(1);
			if (_la==INITIALIZER) {
				{
				setState(220);
				match(INITIALIZER);
				setState(221);
				((LibraryDefinitionContext)_localctx).libInit = validName();
				}
			}

			setState(226);
			_la = _input.LA(1);
			if (_la==REQUIRES) {
				{
				setState(224);
				match(REQUIRES);
				setState(225);
				libraryRequirements();
				}
			}

			setState(228);
			match(NL);
			setState(232);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << PRIVATE) | (1L << PUBLIC) | (1L << GLOBALS) | (1L << NATIVE) | (1L << CONSTANT) | (1L << STATIC) | (1L << STRUCT) | (1L << METHOD) | (1L << INTERFACE) | (1L << FUNCTION))) != 0) || _la==NL) {
				{
				{
				setState(229);
				libraryStatement();
				}
				}
				setState(234);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(235);
			_la = _input.LA(1);
			if ( !(_la==END || _la==ENDLIBRARY) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(236);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceDefinitionContext extends ParserRuleContext {
		public TerminalNode INTERFACE() { return getToken(vrjassParser.INTERFACE, 0); }
		public ValidNameContext validName() {
			return getRuleContext(ValidNameContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(vrjassParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(vrjassParser.NL, i);
		}
		public TerminalNode ENDINTERFACE() { return getToken(vrjassParser.ENDINTERFACE, 0); }
		public TerminalNode END() { return getToken(vrjassParser.END, 0); }
		public List<FunctionSignatureContext> functionSignature() {
			return getRuleContexts(FunctionSignatureContext.class);
		}
		public FunctionSignatureContext functionSignature(int i) {
			return getRuleContext(FunctionSignatureContext.class,i);
		}
		public TerminalNode PRIVATE() { return getToken(vrjassParser.PRIVATE, 0); }
		public TerminalNode PUBLIC() { return getToken(vrjassParser.PUBLIC, 0); }
		public InterfaceDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitInterfaceDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceDefinitionContext interfaceDefinition() throws RecognitionException {
		InterfaceDefinitionContext _localctx = new InterfaceDefinitionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_interfaceDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			_la = _input.LA(1);
			if (_la==PRIVATE || _la==PUBLIC) {
				{
				setState(238);
				_la = _input.LA(1);
				if ( !(_la==PRIVATE || _la==PUBLIC) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
			}

			setState(241);
			match(INTERFACE);
			setState(242);
			validName();
			setState(243);
			match(NL);
			setState(248);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << PRIVATE) | (1L << PUBLIC) | (1L << NATIVE) | (1L << CONSTANT) | (1L << STATIC) | (1L << METHOD) | (1L << FUNCTION))) != 0) || _la==NL) {
				{
				setState(246);
				switch (_input.LA(1)) {
				case NL:
					{
					setState(244);
					match(NL);
					}
					break;
				case ABSTRACT:
				case PRIVATE:
				case PUBLIC:
				case NATIVE:
				case CONSTANT:
				case STATIC:
				case METHOD:
				case FUNCTION:
					{
					setState(245);
					functionSignature();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(250);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(251);
			_la = _input.LA(1);
			if ( !(_la==END || _la==ENDINTERFACE) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(252);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyStatementContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public TerminalNode STATIC() { return getToken(vrjassParser.STATIC, 0); }
		public TerminalNode PRIVATE() { return getToken(vrjassParser.PRIVATE, 0); }
		public TerminalNode PUBLIC() { return getToken(vrjassParser.PUBLIC, 0); }
		public PropertyStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitPropertyStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyStatementContext propertyStatement() throws RecognitionException {
		PropertyStatementContext _localctx = new PropertyStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_propertyStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(255);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(254);
				_la = _input.LA(1);
				if ( !(_la==PRIVATE || _la==PUBLIC) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			}
			setState(258);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(257);
				match(STATIC);
				}
				break;
			}
			setState(260);
			variableDeclaration();
			setState(261);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructStatementContext extends ParserRuleContext {
		public PropertyStatementContext propertyStatement() {
			return getRuleContext(PropertyStatementContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public FunctionSignatureContext functionSignature() {
			return getRuleContext(FunctionSignatureContext.class,0);
		}
		public StructStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitStructStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructStatementContext structStatement() throws RecognitionException {
		StructStatementContext _localctx = new StructStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_structStatement);
		try {
			setState(266);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(263);
				propertyStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(264);
				functionDefinition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(265);
				functionSignature();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImplementsListContext extends ParserRuleContext {
		public List<ValidNameContext> validName() {
			return getRuleContexts(ValidNameContext.class);
		}
		public ValidNameContext validName(int i) {
			return getRuleContext(ValidNameContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(vrjassParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(vrjassParser.COMMA, i);
		}
		public ImplementsListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_implementsList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitImplementsList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImplementsListContext implementsList() throws RecognitionException {
		ImplementsListContext _localctx = new ImplementsListContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_implementsList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			validName();
			setState(273);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(269);
				match(COMMA);
				setState(270);
				validName();
				}
				}
				setState(275);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructDefinitionContext extends ParserRuleContext {
		public ValidNameContext name;
		public ValidNameContext extendsFrom;
		public TerminalNode STRUCT() { return getToken(vrjassParser.STRUCT, 0); }
		public List<TerminalNode> NL() { return getTokens(vrjassParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(vrjassParser.NL, i);
		}
		public List<ValidNameContext> validName() {
			return getRuleContexts(ValidNameContext.class);
		}
		public ValidNameContext validName(int i) {
			return getRuleContext(ValidNameContext.class,i);
		}
		public TerminalNode ENDSTRUCT() { return getToken(vrjassParser.ENDSTRUCT, 0); }
		public TerminalNode END() { return getToken(vrjassParser.END, 0); }
		public TerminalNode ABSTRACT() { return getToken(vrjassParser.ABSTRACT, 0); }
		public TerminalNode EXTENDS() { return getToken(vrjassParser.EXTENDS, 0); }
		public TerminalNode IMPLEMENTS() { return getToken(vrjassParser.IMPLEMENTS, 0); }
		public ImplementsListContext implementsList() {
			return getRuleContext(ImplementsListContext.class,0);
		}
		public List<StructStatementContext> structStatement() {
			return getRuleContexts(StructStatementContext.class);
		}
		public StructStatementContext structStatement(int i) {
			return getRuleContext(StructStatementContext.class,i);
		}
		public TerminalNode PRIVATE() { return getToken(vrjassParser.PRIVATE, 0); }
		public TerminalNode PUBLIC() { return getToken(vrjassParser.PUBLIC, 0); }
		public StructDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitStructDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructDefinitionContext structDefinition() throws RecognitionException {
		StructDefinitionContext _localctx = new StructDefinitionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_structDefinition);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			_la = _input.LA(1);
			if (_la==PRIVATE || _la==PUBLIC) {
				{
				setState(276);
				_la = _input.LA(1);
				if ( !(_la==PRIVATE || _la==PUBLIC) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
			}

			setState(280);
			_la = _input.LA(1);
			if (_la==ABSTRACT) {
				{
				setState(279);
				match(ABSTRACT);
				}
			}

			setState(282);
			match(STRUCT);
			setState(283);
			((StructDefinitionContext)_localctx).name = validName();
			setState(286);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(284);
				match(EXTENDS);
				setState(285);
				((StructDefinitionContext)_localctx).extendsFrom = validName();
				}
			}

			setState(290);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(288);
				match(IMPLEMENTS);
				setState(289);
				implementsList();
				}
			}

			setState(292);
			match(NL);
			setState(297);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(295);
					switch (_input.LA(1)) {
					case NL:
						{
						setState(293);
						match(NL);
						}
						break;
					case ABSTRACT:
					case END:
					case THIS:
					case PRIVATE:
					case PUBLIC:
					case NULL:
					case NOT:
					case NATIVE:
					case CONSTANT:
					case STATIC:
					case METHOD:
					case FUNCTION:
					case TRUE:
					case FALSE:
					case PAREN_LEFT:
					case MINUS:
					case ID:
					case STRING:
					case REAL:
					case INT:
						{
						setState(294);
						structStatement();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					} 
				}
				setState(299);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			}
			setState(300);
			_la = _input.LA(1);
			if ( !(_la==END || _la==ENDSTRUCT) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(301);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnTypeContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode NOTHING() { return getToken(vrjassParser.NOTHING, 0); }
		public ReturnTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitReturnType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnTypeContext returnType() throws RecognitionException {
		ReturnTypeContext _localctx = new ReturnTypeContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_returnType);
		try {
			setState(305);
			switch (_input.LA(1)) {
			case ABSTRACT:
			case END:
			case THIS:
			case PRIVATE:
			case PUBLIC:
			case NULL:
			case NOT:
			case NATIVE:
			case CONSTANT:
			case STATIC:
			case METHOD:
			case FUNCTION:
			case TRUE:
			case FALSE:
			case PAREN_LEFT:
			case MINUS:
			case ID:
			case STRING:
			case REAL:
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(303);
				expression(0);
				}
				break;
			case NOTHING:
				enterOuterAlt(_localctx, 2);
				{
				setState(304);
				match(NOTHING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			variableDeclaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParametersContext extends ParserRuleContext {
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(vrjassParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(vrjassParser.COMMA, i);
		}
		public TerminalNode NOTHING() { return getToken(vrjassParser.NOTHING, 0); }
		public ParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametersContext parameters() throws RecognitionException {
		ParametersContext _localctx = new ParametersContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_parameters);
		int _la;
		try {
			setState(318);
			switch (_input.LA(1)) {
			case ABSTRACT:
			case END:
			case THIS:
			case PRIVATE:
			case PUBLIC:
			case NULL:
			case NOT:
			case NATIVE:
			case CONSTANT:
			case STATIC:
			case METHOD:
			case FUNCTION:
			case TRUE:
			case FALSE:
			case PAREN_LEFT:
			case MINUS:
			case ID:
			case STRING:
			case REAL:
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(309);
				parameter();
				setState(314);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(310);
					match(COMMA);
					setState(311);
					parameter();
					}
					}
					setState(316);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				break;
			case NOTHING:
				enterOuterAlt(_localctx, 2);
				{
				setState(317);
				match(NOTHING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentsContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(vrjassParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(vrjassParser.COMMA, i);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			expression(0);
			setState(325);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(321);
				match(COMMA);
				setState(322);
				expression(0);
				}
				}
				setState(327);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionSignatureContext extends ParserRuleContext {
		public ValidNameContext name;
		public TerminalNode NATIVE() { return getToken(vrjassParser.NATIVE, 0); }
		public TerminalNode FUNCTION() { return getToken(vrjassParser.FUNCTION, 0); }
		public TerminalNode METHOD() { return getToken(vrjassParser.METHOD, 0); }
		public TerminalNode ABSTRACT() { return getToken(vrjassParser.ABSTRACT, 0); }
		public TerminalNode CONSTANT() { return getToken(vrjassParser.CONSTANT, 0); }
		public TerminalNode STATIC() { return getToken(vrjassParser.STATIC, 0); }
		public TerminalNode TAKES() { return getToken(vrjassParser.TAKES, 0); }
		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}
		public TerminalNode RETURNS() { return getToken(vrjassParser.RETURNS, 0); }
		public ReturnTypeContext returnType() {
			return getRuleContext(ReturnTypeContext.class,0);
		}
		public TerminalNode PRIVATE() { return getToken(vrjassParser.PRIVATE, 0); }
		public TerminalNode PUBLIC() { return getToken(vrjassParser.PUBLIC, 0); }
		public ValidNameContext validName() {
			return getRuleContext(ValidNameContext.class,0);
		}
		public FunctionSignatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionSignature; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitFunctionSignature(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionSignatureContext functionSignature() throws RecognitionException {
		FunctionSignatureContext _localctx = new FunctionSignatureContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_functionSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(329);
			_la = _input.LA(1);
			if (_la==PRIVATE || _la==PUBLIC) {
				{
				setState(328);
				_la = _input.LA(1);
				if ( !(_la==PRIVATE || _la==PUBLIC) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
			}

			setState(332);
			_la = _input.LA(1);
			if (_la==ABSTRACT) {
				{
				setState(331);
				match(ABSTRACT);
				}
			}

			setState(335);
			_la = _input.LA(1);
			if (_la==CONSTANT) {
				{
				setState(334);
				match(CONSTANT);
				}
			}

			setState(338);
			_la = _input.LA(1);
			if (_la==STATIC) {
				{
				setState(337);
				match(STATIC);
				}
			}

			setState(340);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NATIVE) | (1L << METHOD) | (1L << FUNCTION))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(342);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				{
				setState(341);
				((FunctionSignatureContext)_localctx).name = validName();
				}
				break;
			}
			setState(346);
			_la = _input.LA(1);
			if (_la==TAKES) {
				{
				setState(344);
				match(TAKES);
				setState(345);
				parameters();
				}
			}

			setState(350);
			_la = _input.LA(1);
			if (_la==RETURNS) {
				{
				setState(348);
				match(RETURNS);
				setState(349);
				returnType();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionExpressionContext extends ParserRuleContext {
		public FunctionSignatureContext functionSignature() {
			return getRuleContext(FunctionSignatureContext.class,0);
		}
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public TerminalNode ENDFUNCTION() { return getToken(vrjassParser.ENDFUNCTION, 0); }
		public TerminalNode ENDMETHOD() { return getToken(vrjassParser.ENDMETHOD, 0); }
		public TerminalNode END() { return getToken(vrjassParser.END, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public FunctionDefinitionExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinitionExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitFunctionDefinitionExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDefinitionExpressionContext functionDefinitionExpression() throws RecognitionException {
		FunctionDefinitionExpressionContext _localctx = new FunctionDefinitionExpressionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_functionDefinitionExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(352);
			functionSignature();
			setState(353);
			match(NL);
			setState(357);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BREAK) | (1L << WHILE) | (1L << LOCAL) | (1L << SET) | (1L << EXITWHEN) | (1L << CALL) | (1L << RETURN) | (1L << IF) | (1L << LOOP))) != 0) || _la==NL) {
				{
				{
				setState(354);
				statement();
				}
				}
				setState(359);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(360);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << END) | (1L << ENDMETHOD) | (1L << ENDFUNCTION))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public FunctionDefinitionExpressionContext functionDefinitionExpression() {
			return getRuleContext(FunctionDefinitionExpressionContext.class,0);
		}
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitFunctionDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_functionDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(362);
			functionDefinitionExpression();
			setState(363);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public LocalVariableStatementContext localVariableStatement() {
			return getRuleContext(LocalVariableStatementContext.class,0);
		}
		public AssignmentStatementContext assignmentStatement() {
			return getRuleContext(AssignmentStatementContext.class,0);
		}
		public FunctionStatementContext functionStatement() {
			return getRuleContext(FunctionStatementContext.class,0);
		}
		public LoopStatementContext loopStatement() {
			return getRuleContext(LoopStatementContext.class,0);
		}
		public WhileLoopStatementContext whileLoopStatement() {
			return getRuleContext(WhileLoopStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public ExitWhenStatementContext exitWhenStatement() {
			return getRuleContext(ExitWhenStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_statement);
		try {
			setState(375);
			switch (_input.LA(1)) {
			case NL:
				enterOuterAlt(_localctx, 1);
				{
				setState(365);
				match(NL);
				}
				break;
			case LOCAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(366);
				localVariableStatement();
				}
				break;
			case SET:
				enterOuterAlt(_localctx, 3);
				{
				setState(367);
				assignmentStatement();
				}
				break;
			case CALL:
				enterOuterAlt(_localctx, 4);
				{
				setState(368);
				functionStatement();
				}
				break;
			case LOOP:
				enterOuterAlt(_localctx, 5);
				{
				setState(369);
				loopStatement();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 6);
				{
				setState(370);
				whileLoopStatement();
				}
				break;
			case BREAK:
				enterOuterAlt(_localctx, 7);
				{
				setState(371);
				breakStatement();
				}
				break;
			case EXITWHEN:
				enterOuterAlt(_localctx, 8);
				{
				setState(372);
				exitWhenStatement();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 9);
				{
				setState(373);
				ifStatement();
				}
				break;
			case RETURN:
				enterOuterAlt(_localctx, 10);
				{
				setState(374);
				returnStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LocalVariableStatementContext extends ParserRuleContext {
		public TerminalNode LOCAL() { return getToken(vrjassParser.LOCAL, 0); }
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public LocalVariableStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localVariableStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitLocalVariableStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LocalVariableStatementContext localVariableStatement() throws RecognitionException {
		LocalVariableStatementContext _localctx = new LocalVariableStatementContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_localVariableStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(377);
			match(LOCAL);
			setState(378);
			variableDeclaration();
			setState(379);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentStatementContext extends ParserRuleContext {
		public ExpressionContext name;
		public Token operator;
		public ExpressionContext value;
		public TerminalNode SET() { return getToken(vrjassParser.SET, 0); }
		public TerminalNode EQ() { return getToken(vrjassParser.EQ, 0); }
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(vrjassParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(vrjassParser.MINUS, 0); }
		public TerminalNode TIMES() { return getToken(vrjassParser.TIMES, 0); }
		public TerminalNode DIV() { return getToken(vrjassParser.DIV, 0); }
		public AssignmentStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitAssignmentStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentStatementContext assignmentStatement() throws RecognitionException {
		AssignmentStatementContext _localctx = new AssignmentStatementContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_assignmentStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
			match(SET);
			setState(382);
			((AssignmentStatementContext)_localctx).name = expression(0);
			setState(384);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DIV) | (1L << TIMES) | (1L << MINUS) | (1L << PLUS))) != 0)) {
				{
				setState(383);
				((AssignmentStatementContext)_localctx).operator = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DIV) | (1L << TIMES) | (1L << MINUS) | (1L << PLUS))) != 0)) ) {
					((AssignmentStatementContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
			}

			setState(386);
			match(EQ);
			setState(387);
			((AssignmentStatementContext)_localctx).value = expression(0);
			setState(388);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionStatementContext extends ParserRuleContext {
		public TerminalNode CALL() { return getToken(vrjassParser.CALL, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public FunctionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitFunctionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionStatementContext functionStatement() throws RecognitionException {
		FunctionStatementContext _localctx = new FunctionStatementContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_functionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
			match(CALL);
			setState(391);
			expression(0);
			setState(392);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode BREAK() { return getToken(vrjassParser.BREAK, 0); }
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(394);
			match(BREAK);
			setState(395);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExitWhenStatementContext extends ParserRuleContext {
		public TerminalNode EXITWHEN() { return getToken(vrjassParser.EXITWHEN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public ExitWhenStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exitWhenStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitExitWhenStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExitWhenStatementContext exitWhenStatement() throws RecognitionException {
		ExitWhenStatementContext _localctx = new ExitWhenStatementContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_exitWhenStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(397);
			match(EXITWHEN);
			setState(398);
			expression(0);
			setState(399);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopStatementContext extends ParserRuleContext {
		public TerminalNode LOOP() { return getToken(vrjassParser.LOOP, 0); }
		public List<TerminalNode> NL() { return getTokens(vrjassParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(vrjassParser.NL, i);
		}
		public TerminalNode ENDLOOP() { return getToken(vrjassParser.ENDLOOP, 0); }
		public TerminalNode END() { return getToken(vrjassParser.END, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public LoopStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitLoopStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopStatementContext loopStatement() throws RecognitionException {
		LoopStatementContext _localctx = new LoopStatementContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_loopStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(401);
			match(LOOP);
			setState(402);
			match(NL);
			setState(406);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BREAK) | (1L << WHILE) | (1L << LOCAL) | (1L << SET) | (1L << EXITWHEN) | (1L << CALL) | (1L << RETURN) | (1L << IF) | (1L << LOOP))) != 0) || _la==NL) {
				{
				{
				setState(403);
				statement();
				}
				}
				setState(408);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(409);
			_la = _input.LA(1);
			if ( !(_la==END || _la==ENDLOOP) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(410);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileLoopStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(vrjassParser.WHILE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(vrjassParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(vrjassParser.NL, i);
		}
		public TerminalNode ENDWHILE() { return getToken(vrjassParser.ENDWHILE, 0); }
		public TerminalNode END() { return getToken(vrjassParser.END, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public WhileLoopStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileLoopStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitWhileLoopStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileLoopStatementContext whileLoopStatement() throws RecognitionException {
		WhileLoopStatementContext _localctx = new WhileLoopStatementContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_whileLoopStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(412);
			match(WHILE);
			setState(413);
			expression(0);
			setState(414);
			match(NL);
			setState(418);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BREAK) | (1L << WHILE) | (1L << LOCAL) | (1L << SET) | (1L << EXITWHEN) | (1L << CALL) | (1L << RETURN) | (1L << IF) | (1L << LOOP))) != 0) || _la==NL) {
				{
				{
				setState(415);
				statement();
				}
				}
				setState(420);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(421);
			_la = _input.LA(1);
			if ( !(_la==ENDWHILE || _la==END) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(422);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(vrjassParser.IF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode THEN() { return getToken(vrjassParser.THEN, 0); }
		public List<TerminalNode> NL() { return getTokens(vrjassParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(vrjassParser.NL, i);
		}
		public TerminalNode ENDIF() { return getToken(vrjassParser.ENDIF, 0); }
		public TerminalNode END() { return getToken(vrjassParser.END, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<ElseIfStatementContext> elseIfStatement() {
			return getRuleContexts(ElseIfStatementContext.class);
		}
		public ElseIfStatementContext elseIfStatement(int i) {
			return getRuleContext(ElseIfStatementContext.class,i);
		}
		public ElseStatementContext elseStatement() {
			return getRuleContext(ElseStatementContext.class,0);
		}
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(424);
			match(IF);
			setState(425);
			expression(0);
			setState(426);
			match(THEN);
			setState(427);
			match(NL);
			setState(431);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BREAK) | (1L << WHILE) | (1L << LOCAL) | (1L << SET) | (1L << EXITWHEN) | (1L << CALL) | (1L << RETURN) | (1L << IF) | (1L << LOOP))) != 0) || _la==NL) {
				{
				{
				setState(428);
				statement();
				}
				}
				setState(433);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(437);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ELSEIF) {
				{
				{
				setState(434);
				elseIfStatement();
				}
				}
				setState(439);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(441);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(440);
				elseStatement();
				}
			}

			setState(443);
			_la = _input.LA(1);
			if ( !(_la==END || _la==ENDIF) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(444);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElseIfStatementContext extends ParserRuleContext {
		public TerminalNode ELSEIF() { return getToken(vrjassParser.ELSEIF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode THEN() { return getToken(vrjassParser.THEN, 0); }
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ElseIfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseIfStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitElseIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseIfStatementContext elseIfStatement() throws RecognitionException {
		ElseIfStatementContext _localctx = new ElseIfStatementContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_elseIfStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(446);
			match(ELSEIF);
			setState(447);
			expression(0);
			setState(448);
			match(THEN);
			setState(449);
			match(NL);
			setState(453);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BREAK) | (1L << WHILE) | (1L << LOCAL) | (1L << SET) | (1L << EXITWHEN) | (1L << CALL) | (1L << RETURN) | (1L << IF) | (1L << LOOP))) != 0) || _la==NL) {
				{
				{
				setState(450);
				statement();
				}
				}
				setState(455);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElseStatementContext extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(vrjassParser.ELSE, 0); }
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ElseStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitElseStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseStatementContext elseStatement() throws RecognitionException {
		ElseStatementContext _localctx = new ElseStatementContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_elseStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(456);
			match(ELSE);
			setState(457);
			match(NL);
			setState(461);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BREAK) | (1L << WHILE) | (1L << LOCAL) | (1L << SET) | (1L << EXITWHEN) | (1L << CALL) | (1L << RETURN) | (1L << IF) | (1L << LOOP))) != 0) || _la==NL) {
				{
				{
				setState(458);
				statement();
				}
				}
				setState(463);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(vrjassParser.RETURN, 0); }
		public TerminalNode NL() { return getToken(vrjassParser.NL, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof vrjassVisitor ) return ((vrjassVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(464);
			match(RETURN);
			setState(466);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << END) | (1L << THIS) | (1L << PRIVATE) | (1L << PUBLIC) | (1L << NULL) | (1L << NOT) | (1L << NATIVE) | (1L << CONSTANT) | (1L << STATIC) | (1L << METHOD) | (1L << FUNCTION) | (1L << TRUE) | (1L << FALSE) | (1L << PAREN_LEFT) | (1L << MINUS))) != 0) || ((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (ID - 72)) | (1L << (STRING - 72)) | (1L << (REAL - 72)) | (1L << (INT - 72)))) != 0)) {
				{
				setState(465);
				expression(0);
				}
			}

			setState(468);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 5:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		case 1:
			return precpred(_ctx, 9);
		case 2:
			return precpred(_ctx, 7);
		case 3:
			return precpred(_ctx, 6);
		case 4:
			return precpred(_ctx, 5);
		case 5:
			return precpred(_ctx, 4);
		case 6:
			return precpred(_ctx, 3);
		case 7:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3P\u01d9\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\7\2P\n\2\f\2\16\2S\13"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3_\n\3\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\5\6g\n\6\3\6\3\6\3\6\5\6l\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u0081\n\7\3\7\3\7\3\7\5"+
		"\7\u0086\n\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u008e\n\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\7\7\u00a8\n\7\f\7\16\7\u00ab\13\7\3\b\3\b\3\b\3\b\5\b\u00b1\n\b"+
		"\3\b\3\b\3\t\3\t\3\t\3\n\5\n\u00b9\n\n\3\n\5\n\u00bc\n\n\3\n\3\n\3\n\3"+
		"\13\3\13\7\13\u00c3\n\13\f\13\16\13\u00c6\13\13\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\r\3\r\3\r\7\r\u00d1\n\r\f\r\16\r\u00d4\13\r\3\16\3\16\3\16\3\16\3"+
		"\16\5\16\u00db\n\16\3\17\3\17\3\17\3\17\5\17\u00e1\n\17\3\17\3\17\5\17"+
		"\u00e5\n\17\3\17\3\17\7\17\u00e9\n\17\f\17\16\17\u00ec\13\17\3\17\3\17"+
		"\3\17\3\20\5\20\u00f2\n\20\3\20\3\20\3\20\3\20\3\20\7\20\u00f9\n\20\f"+
		"\20\16\20\u00fc\13\20\3\20\3\20\3\20\3\21\5\21\u0102\n\21\3\21\5\21\u0105"+
		"\n\21\3\21\3\21\3\21\3\22\3\22\3\22\5\22\u010d\n\22\3\23\3\23\3\23\7\23"+
		"\u0112\n\23\f\23\16\23\u0115\13\23\3\24\5\24\u0118\n\24\3\24\5\24\u011b"+
		"\n\24\3\24\3\24\3\24\3\24\5\24\u0121\n\24\3\24\3\24\5\24\u0125\n\24\3"+
		"\24\3\24\3\24\7\24\u012a\n\24\f\24\16\24\u012d\13\24\3\24\3\24\3\24\3"+
		"\25\3\25\5\25\u0134\n\25\3\26\3\26\3\27\3\27\3\27\7\27\u013b\n\27\f\27"+
		"\16\27\u013e\13\27\3\27\5\27\u0141\n\27\3\30\3\30\3\30\7\30\u0146\n\30"+
		"\f\30\16\30\u0149\13\30\3\31\5\31\u014c\n\31\3\31\5\31\u014f\n\31\3\31"+
		"\5\31\u0152\n\31\3\31\5\31\u0155\n\31\3\31\3\31\5\31\u0159\n\31\3\31\3"+
		"\31\5\31\u015d\n\31\3\31\3\31\5\31\u0161\n\31\3\32\3\32\3\32\7\32\u0166"+
		"\n\32\f\32\16\32\u0169\13\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3"+
		"\34\3\34\3\34\3\34\3\34\3\34\3\34\5\34\u017a\n\34\3\35\3\35\3\35\3\35"+
		"\3\36\3\36\3\36\5\36\u0183\n\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37"+
		"\3 \3 \3 \3!\3!\3!\3!\3\"\3\"\3\"\7\"\u0197\n\"\f\"\16\"\u019a\13\"\3"+
		"\"\3\"\3\"\3#\3#\3#\3#\7#\u01a3\n#\f#\16#\u01a6\13#\3#\3#\3#\3$\3$\3$"+
		"\3$\3$\7$\u01b0\n$\f$\16$\u01b3\13$\3$\7$\u01b6\n$\f$\16$\u01b9\13$\3"+
		"$\5$\u01bc\n$\3$\3$\3$\3%\3%\3%\3%\3%\7%\u01c6\n%\f%\16%\u01c9\13%\3&"+
		"\3&\3&\7&\u01ce\n&\f&\16&\u01d1\13&\3\'\3\'\5\'\u01d5\n\'\3\'\3\'\3\'"+
		"\2\3\f(\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:"+
		"<>@BDFHJL\2\21\4\2\7\7JJ\3\2\66\67\3\2CH\3\2\23\24\3\2\21\22\4\2\7\7\35"+
		"\35\4\2\7\7\n\n\4\2\7\7))\4\2\7\7%%\5\2!!&&**\5\2\7\7\'\'++\3\2>A\4\2"+
		"\7\7\65\65\3\2\6\7\4\2\7\7\63\63\u020b\2Q\3\2\2\2\4^\3\2\2\2\6`\3\2\2"+
		"\2\bb\3\2\2\2\nd\3\2\2\2\f\u008d\3\2\2\2\16\u00ac\3\2\2\2\20\u00b4\3\2"+
		"\2\2\22\u00b8\3\2\2\2\24\u00c4\3\2\2\2\26\u00c7\3\2\2\2\30\u00cd\3\2\2"+
		"\2\32\u00da\3\2\2\2\34\u00dc\3\2\2\2\36\u00f1\3\2\2\2 \u0101\3\2\2\2\""+
		"\u010c\3\2\2\2$\u010e\3\2\2\2&\u0117\3\2\2\2(\u0133\3\2\2\2*\u0135\3\2"+
		"\2\2,\u0140\3\2\2\2.\u0142\3\2\2\2\60\u014b\3\2\2\2\62\u0162\3\2\2\2\64"+
		"\u016c\3\2\2\2\66\u0179\3\2\2\28\u017b\3\2\2\2:\u017f\3\2\2\2<\u0188\3"+
		"\2\2\2>\u018c\3\2\2\2@\u018f\3\2\2\2B\u0193\3\2\2\2D\u019e\3\2\2\2F\u01aa"+
		"\3\2\2\2H\u01c0\3\2\2\2J\u01ca\3\2\2\2L\u01d2\3\2\2\2NP\5\4\3\2ON\3\2"+
		"\2\2PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2RT\3\2\2\2SQ\3\2\2\2TU\7\2\2\3U\3\3"+
		"\2\2\2V_\7I\2\2W_\5\16\b\2X_\5\20\t\2Y_\5\26\f\2Z_\5\34\17\2[_\5\36\20"+
		"\2\\_\5&\24\2]_\5\64\33\2^V\3\2\2\2^W\3\2\2\2^X\3\2\2\2^Y\3\2\2\2^Z\3"+
		"\2\2\2^[\3\2\2\2^\\\3\2\2\2^]\3\2\2\2_\5\3\2\2\2`a\5\f\7\2a\7\3\2\2\2"+
		"bc\t\2\2\2c\t\3\2\2\2df\5\6\4\2eg\7\27\2\2fe\3\2\2\2fg\3\2\2\2gh\3\2\2"+
		"\2hk\5\b\5\2ij\7B\2\2jl\5\f\7\2ki\3\2\2\2kl\3\2\2\2l\13\3\2\2\2mn\b\7"+
		"\1\2no\7@\2\2o\u008e\5\f\7\23pq\7\26\2\2q\u008e\5\f\7\22rs\7*\2\2s\u008e"+
		"\5\f\7\nt\u008e\5\62\32\2u\u008e\7M\2\2v\u008e\7L\2\2w\u008e\7K\2\2x\u008e"+
		"\t\3\2\2y\u008e\7\25\2\2z\u008e\7\r\2\2{\u0080\5\b\5\2|}\7;\2\2}~\5\f"+
		"\7\2~\177\7<\2\2\177\u0081\3\2\2\2\u0080|\3\2\2\2\u0080\u0081\3\2\2\2"+
		"\u0081\u008e\3\2\2\2\u0082\u0083\5\b\5\2\u0083\u0085\79\2\2\u0084\u0086"+
		"\5.\30\2\u0085\u0084\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u0087\3\2\2\2\u0087"+
		"\u0088\7:\2\2\u0088\u008e\3\2\2\2\u0089\u008a\79\2\2\u008a\u008b\5\f\7"+
		"\2\u008b\u008c\7:\2\2\u008c\u008e\3\2\2\2\u008dm\3\2\2\2\u008dp\3\2\2"+
		"\2\u008dr\3\2\2\2\u008dt\3\2\2\2\u008du\3\2\2\2\u008dv\3\2\2\2\u008dw"+
		"\3\2\2\2\u008dx\3\2\2\2\u008dy\3\2\2\2\u008dz\3\2\2\2\u008d{\3\2\2\2\u008d"+
		"\u0082\3\2\2\2\u008d\u0089\3\2\2\2\u008e\u00a9\3\2\2\2\u008f\u0090\f\f"+
		"\2\2\u0090\u0091\7\20\2\2\u0091\u00a8\5\f\7\r\u0092\u0093\f\13\2\2\u0093"+
		"\u0094\7\b\2\2\u0094\u00a8\5\f\7\f\u0095\u0096\f\t\2\2\u0096\u0097\7>"+
		"\2\2\u0097\u00a8\5\f\7\n\u0098\u0099\f\b\2\2\u0099\u009a\7?\2\2\u009a"+
		"\u00a8\5\f\7\t\u009b\u009c\f\7\2\2\u009c\u009d\7@\2\2\u009d\u00a8\5\f"+
		"\7\b\u009e\u009f\f\6\2\2\u009f\u00a0\7A\2\2\u00a0\u00a8\5\f\7\7\u00a1"+
		"\u00a2\f\5\2\2\u00a2\u00a3\t\4\2\2\u00a3\u00a8\5\f\7\6\u00a4\u00a5\f\4"+
		"\2\2\u00a5\u00a6\t\5\2\2\u00a6\u00a8\5\f\7\5\u00a7\u008f\3\2\2\2\u00a7"+
		"\u0092\3\2\2\2\u00a7\u0095\3\2\2\2\u00a7\u0098\3\2\2\2\u00a7\u009b\3\2"+
		"\2\2\u00a7\u009e\3\2\2\2\u00a7\u00a1\3\2\2\2\u00a7\u00a4\3\2\2\2\u00a8"+
		"\u00ab\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\r\3\2\2\2"+
		"\u00ab\u00a9\3\2\2\2\u00ac\u00ad\7 \2\2\u00ad\u00b0\5\b\5\2\u00ae\u00af"+
		"\7\36\2\2\u00af\u00b1\5\6\4\2\u00b0\u00ae\3\2\2\2\u00b0\u00b1\3\2\2\2"+
		"\u00b1\u00b2\3\2\2\2\u00b2\u00b3\7I\2\2\u00b3\17\3\2\2\2\u00b4\u00b5\5"+
		"\60\31\2\u00b5\u00b6\7I\2\2\u00b6\21\3\2\2\2\u00b7\u00b9\t\6\2\2\u00b8"+
		"\u00b7\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00bb\3\2\2\2\u00ba\u00bc\7\""+
		"\2\2\u00bb\u00ba\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd"+
		"\u00be\5\n\6\2\u00be\u00bf\7I\2\2\u00bf\23\3\2\2\2\u00c0\u00c3\7I\2\2"+
		"\u00c1\u00c3\5\22\n\2\u00c2\u00c0\3\2\2\2\u00c2\u00c1\3\2\2\2\u00c3\u00c6"+
		"\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\25\3\2\2\2\u00c6"+
		"\u00c4\3\2\2\2\u00c7\u00c8\7\34\2\2\u00c8\u00c9\7I\2\2\u00c9\u00ca\5\24"+
		"\13\2\u00ca\u00cb\t\7\2\2\u00cb\u00cc\7I\2\2\u00cc\27\3\2\2\2\u00cd\u00d2"+
		"\5\b\5\2\u00ce\u00cf\7=\2\2\u00cf\u00d1\5\b\5\2\u00d0\u00ce\3\2\2\2\u00d1"+
		"\u00d4\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\31\3\2\2"+
		"\2\u00d4\u00d2\3\2\2\2\u00d5\u00db\7I\2\2\u00d6\u00db\5\26\f\2\u00d7\u00db"+
		"\5\36\20\2\u00d8\u00db\5&\24\2\u00d9\u00db\5\64\33\2\u00da\u00d5\3\2\2"+
		"\2\u00da\u00d6\3\2\2\2\u00da\u00d7\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00d9"+
		"\3\2\2\2\u00db\33\3\2\2\2\u00dc\u00dd\7\t\2\2\u00dd\u00e0\5\b\5\2\u00de"+
		"\u00df\7\13\2\2\u00df\u00e1\5\b\5\2\u00e0\u00de\3\2\2\2\u00e0\u00e1\3"+
		"\2\2\2\u00e1\u00e4\3\2\2\2\u00e2\u00e3\7\f\2\2\u00e3\u00e5\5\30\r\2\u00e4"+
		"\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00ea\7I"+
		"\2\2\u00e7\u00e9\5\32\16\2\u00e8\u00e7\3\2\2\2\u00e9\u00ec\3\2\2\2\u00ea"+
		"\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ed\3\2\2\2\u00ec\u00ea\3\2"+
		"\2\2\u00ed\u00ee\t\b\2\2\u00ee\u00ef\7I\2\2\u00ef\35\3\2\2\2\u00f0\u00f2"+
		"\t\6\2\2\u00f1\u00f0\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3"+
		"\u00f4\7(\2\2\u00f4\u00f5\5\b\5\2\u00f5\u00fa\7I\2\2\u00f6\u00f9\7I\2"+
		"\2\u00f7\u00f9\5\60\31\2\u00f8\u00f6\3\2\2\2\u00f8\u00f7\3\2\2\2\u00f9"+
		"\u00fc\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb\u00fd\3\2"+
		"\2\2\u00fc\u00fa\3\2\2\2\u00fd\u00fe\t\t\2\2\u00fe\u00ff\7I\2\2\u00ff"+
		"\37\3\2\2\2\u0100\u0102\t\6\2\2\u0101\u0100\3\2\2\2\u0101\u0102\3\2\2"+
		"\2\u0102\u0104\3\2\2\2\u0103\u0105\7#\2\2\u0104\u0103\3\2\2\2\u0104\u0105"+
		"\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0107\5\n\6\2\u0107\u0108\7I\2\2\u0108"+
		"!\3\2\2\2\u0109\u010d\5 \21\2\u010a\u010d\5\64\33\2\u010b\u010d\5\60\31"+
		"\2\u010c\u0109\3\2\2\2\u010c\u010a\3\2\2\2\u010c\u010b\3\2\2\2\u010d#"+
		"\3\2\2\2\u010e\u0113\5\b\5\2\u010f\u0110\7=\2\2\u0110\u0112\5\b\5\2\u0111"+
		"\u010f\3\2\2\2\u0112\u0115\3\2\2\2\u0113\u0111\3\2\2\2\u0113\u0114\3\2"+
		"\2\2\u0114%\3\2\2\2\u0115\u0113\3\2\2\2\u0116\u0118\t\6\2\2\u0117\u0116"+
		"\3\2\2\2\u0117\u0118\3\2\2\2\u0118\u011a\3\2\2\2\u0119\u011b\7\3\2\2\u011a"+
		"\u0119\3\2\2\2\u011a\u011b\3\2\2\2\u011b\u011c\3\2\2\2\u011c\u011d\7$"+
		"\2\2\u011d\u0120\5\b\5\2\u011e\u011f\7\36\2\2\u011f\u0121\5\b\5\2\u0120"+
		"\u011e\3\2\2\2\u0120\u0121\3\2\2\2\u0121\u0124\3\2\2\2\u0122\u0123\7\37"+
		"\2\2\u0123\u0125\5$\23\2\u0124\u0122\3\2\2\2\u0124\u0125\3\2\2\2\u0125"+
		"\u0126\3\2\2\2\u0126\u012b\7I\2\2\u0127\u012a\7I\2\2\u0128\u012a\5\"\22"+
		"\2\u0129\u0127\3\2\2\2\u0129\u0128\3\2\2\2\u012a\u012d\3\2\2\2\u012b\u0129"+
		"\3\2\2\2\u012b\u012c\3\2\2\2\u012c\u012e\3\2\2\2\u012d\u012b\3\2\2\2\u012e"+
		"\u012f\t\n\2\2\u012f\u0130\7I\2\2\u0130\'\3\2\2\2\u0131\u0134\5\f\7\2"+
		"\u0132\u0134\78\2\2\u0133\u0131\3\2\2\2\u0133\u0132\3\2\2\2\u0134)\3\2"+
		"\2\2\u0135\u0136\5\n\6\2\u0136+\3\2\2\2\u0137\u013c\5*\26\2\u0138\u0139"+
		"\7=\2\2\u0139\u013b\5*\26\2\u013a\u0138\3\2\2\2\u013b\u013e\3\2\2\2\u013c"+
		"\u013a\3\2\2\2\u013c\u013d\3\2\2\2\u013d\u0141\3\2\2\2\u013e\u013c\3\2"+
		"\2\2\u013f\u0141\78\2\2\u0140\u0137\3\2\2\2\u0140\u013f\3\2\2\2\u0141"+
		"-\3\2\2\2\u0142\u0147\5\f\7\2\u0143\u0144\7=\2\2\u0144\u0146\5\f\7\2\u0145"+
		"\u0143\3\2\2\2\u0146\u0149\3\2\2\2\u0147\u0145\3\2\2\2\u0147\u0148\3\2"+
		"\2\2\u0148/\3\2\2\2\u0149\u0147\3\2\2\2\u014a\u014c\t\6\2\2\u014b\u014a"+
		"\3\2\2\2\u014b\u014c\3\2\2\2\u014c\u014e\3\2\2\2\u014d\u014f\7\3\2\2\u014e"+
		"\u014d\3\2\2\2\u014e\u014f\3\2\2\2\u014f\u0151\3\2\2\2\u0150\u0152\7\""+
		"\2\2\u0151\u0150\3\2\2\2\u0151\u0152\3\2\2\2\u0152\u0154\3\2\2\2\u0153"+
		"\u0155\7#\2\2\u0154\u0153\3\2\2\2\u0154\u0155\3\2\2\2\u0155\u0156\3\2"+
		"\2\2\u0156\u0158\t\13\2\2\u0157\u0159\5\b\5\2\u0158\u0157\3\2\2\2\u0158"+
		"\u0159\3\2\2\2\u0159\u015c\3\2\2\2\u015a\u015b\7,\2\2\u015b\u015d\5,\27"+
		"\2\u015c\u015a\3\2\2\2\u015c\u015d\3\2\2\2\u015d\u0160\3\2\2\2\u015e\u015f"+
		"\7-\2\2\u015f\u0161\5(\25\2\u0160\u015e\3\2\2\2\u0160\u0161\3\2\2\2\u0161"+
		"\61\3\2\2\2\u0162\u0163\5\60\31\2\u0163\u0167\7I\2\2\u0164\u0166\5\66"+
		"\34\2\u0165\u0164\3\2\2\2\u0166\u0169\3\2\2\2\u0167\u0165\3\2\2\2\u0167"+
		"\u0168\3\2\2\2\u0168\u016a\3\2\2\2\u0169\u0167\3\2\2\2\u016a\u016b\t\f"+
		"\2\2\u016b\63\3\2\2\2\u016c\u016d\5\62\32\2\u016d\u016e\7I\2\2\u016e\65"+
		"\3\2\2\2\u016f\u017a\7I\2\2\u0170\u017a\58\35\2\u0171\u017a\5:\36\2\u0172"+
		"\u017a\5<\37\2\u0173\u017a\5B\"\2\u0174\u017a\5D#\2\u0175\u017a\5> \2"+
		"\u0176\u017a\5@!\2\u0177\u017a\5F$\2\u0178\u017a\5L\'\2\u0179\u016f\3"+
		"\2\2\2\u0179\u0170\3\2\2\2\u0179\u0171\3\2\2\2\u0179\u0172\3\2\2\2\u0179"+
		"\u0173\3\2\2\2\u0179\u0174\3\2\2\2\u0179\u0175\3\2\2\2\u0179\u0176\3\2"+
		"\2\2\u0179\u0177\3\2\2\2\u0179\u0178\3\2\2\2\u017a\67\3\2\2\2\u017b\u017c"+
		"\7\30\2\2\u017c\u017d\5\n\6\2\u017d\u017e\7I\2\2\u017e9\3\2\2\2\u017f"+
		"\u0180\7\31\2\2\u0180\u0182\5\f\7\2\u0181\u0183\t\r\2\2\u0182\u0181\3"+
		"\2\2\2\u0182\u0183\3\2\2\2\u0183\u0184\3\2\2\2\u0184\u0185\7B\2\2\u0185"+
		"\u0186\5\f\7\2\u0186\u0187\7I\2\2\u0187;\3\2\2\2\u0188\u0189\7\33\2\2"+
		"\u0189\u018a\5\f\7\2\u018a\u018b\7I\2\2\u018b=\3\2\2\2\u018c\u018d\7\4"+
		"\2\2\u018d\u018e\7I\2\2\u018e?\3\2\2\2\u018f\u0190\7\32\2\2\u0190\u0191"+
		"\5\f\7\2\u0191\u0192\7I\2\2\u0192A\3\2\2\2\u0193\u0194\7\64\2\2\u0194"+
		"\u0198\7I\2\2\u0195\u0197\5\66\34\2\u0196\u0195\3\2\2\2\u0197\u019a\3"+
		"\2\2\2\u0198\u0196\3\2\2\2\u0198\u0199\3\2\2\2\u0199\u019b\3\2\2\2\u019a"+
		"\u0198\3\2\2\2\u019b\u019c\t\16\2\2\u019c\u019d\7I\2\2\u019dC\3\2\2\2"+
		"\u019e\u019f\7\5\2\2\u019f\u01a0\5\f\7\2\u01a0\u01a4\7I\2\2\u01a1\u01a3"+
		"\5\66\34\2\u01a2\u01a1\3\2\2\2\u01a3\u01a6\3\2\2\2\u01a4\u01a2\3\2\2\2"+
		"\u01a4\u01a5\3\2\2\2\u01a5\u01a7\3\2\2\2\u01a6\u01a4\3\2\2\2\u01a7\u01a8"+
		"\t\17\2\2\u01a8\u01a9\7I\2\2\u01a9E\3\2\2\2\u01aa\u01ab\7/\2\2\u01ab\u01ac"+
		"\5\f\7\2\u01ac\u01ad\7\60\2\2\u01ad\u01b1\7I\2\2\u01ae\u01b0\5\66\34\2"+
		"\u01af\u01ae\3\2\2\2\u01b0\u01b3\3\2\2\2\u01b1\u01af\3\2\2\2\u01b1\u01b2"+
		"\3\2\2\2\u01b2\u01b7\3\2\2\2\u01b3\u01b1\3\2\2\2\u01b4\u01b6\5H%\2\u01b5"+
		"\u01b4\3\2\2\2\u01b6\u01b9\3\2\2\2\u01b7\u01b5\3\2\2\2\u01b7\u01b8\3\2"+
		"\2\2\u01b8\u01bb\3\2\2\2\u01b9\u01b7\3\2\2\2\u01ba\u01bc\5J&\2\u01bb\u01ba"+
		"\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc\u01bd\3\2\2\2\u01bd\u01be\t\20\2\2"+
		"\u01be\u01bf\7I\2\2\u01bfG\3\2\2\2\u01c0\u01c1\7\61\2\2\u01c1\u01c2\5"+
		"\f\7\2\u01c2\u01c3\7\60\2\2\u01c3\u01c7\7I\2\2\u01c4\u01c6\5\66\34\2\u01c5"+
		"\u01c4\3\2\2\2\u01c6\u01c9\3\2\2\2\u01c7\u01c5\3\2\2\2\u01c7\u01c8\3\2"+
		"\2\2\u01c8I\3\2\2\2\u01c9\u01c7\3\2\2\2\u01ca\u01cb\7\62\2\2\u01cb\u01cf"+
		"\7I\2\2\u01cc\u01ce\5\66\34\2\u01cd\u01cc\3\2\2\2\u01ce\u01d1\3\2\2\2"+
		"\u01cf\u01cd\3\2\2\2\u01cf\u01d0\3\2\2\2\u01d0K\3\2\2\2\u01d1\u01cf\3"+
		"\2\2\2\u01d2\u01d4\7.\2\2\u01d3\u01d5\5\f\7\2\u01d4\u01d3\3\2\2\2\u01d4"+
		"\u01d5\3\2\2\2\u01d5\u01d6\3\2\2\2\u01d6\u01d7\7I\2\2\u01d7M\3\2\2\28"+
		"Q^fk\u0080\u0085\u008d\u00a7\u00a9\u00b0\u00b8\u00bb\u00c2\u00c4\u00d2"+
		"\u00da\u00e0\u00e4\u00ea\u00f1\u00f8\u00fa\u0101\u0104\u010c\u0113\u0117"+
		"\u011a\u0120\u0124\u0129\u012b\u0133\u013c\u0140\u0147\u014b\u014e\u0151"+
		"\u0154\u0158\u015c\u0160\u0167\u0179\u0182\u0198\u01a4\u01b1\u01b7\u01bb"+
		"\u01c7\u01cf\u01d4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}