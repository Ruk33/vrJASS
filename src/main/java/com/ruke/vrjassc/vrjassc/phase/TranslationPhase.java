package com.ruke.vrjassc.vrjassc.phase;

import com.ruke.vrjassc.translator.expression.AssignmentStatement;
import com.ruke.vrjassc.translator.expression.BooleanExpression;
import com.ruke.vrjassc.translator.expression.ChainExpression;
import com.ruke.vrjassc.translator.expression.ElseIfStatement;
import com.ruke.vrjassc.translator.expression.ElseStatement;
import com.ruke.vrjassc.translator.expression.ExitWhenStatement;
import com.ruke.vrjassc.translator.expression.Expression;
import com.ruke.vrjassc.translator.expression.ExpressionList;
import com.ruke.vrjassc.translator.expression.FunctionDefinition;
import com.ruke.vrjassc.translator.expression.FunctionExpression;
import com.ruke.vrjassc.translator.expression.FunctionStatement;
import com.ruke.vrjassc.translator.expression.IfStatement;
import com.ruke.vrjassc.translator.expression.InitializerList;
import com.ruke.vrjassc.translator.expression.JassContainer;
import com.ruke.vrjassc.translator.expression.LoopStatement;
import com.ruke.vrjassc.translator.expression.MathExpression;
import com.ruke.vrjassc.translator.expression.MathExpression.Operator;
import com.ruke.vrjassc.translator.expression.ParenthesisExpression;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.ReturnStatement;
import com.ruke.vrjassc.translator.expression.Statement;
import com.ruke.vrjassc.translator.expression.StatementBody;
import com.ruke.vrjassc.translator.expression.VariableExpression;
import com.ruke.vrjassc.translator.expression.VariableStatement;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ArgumentsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.BooleanContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.BooleanExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.CallFunctionStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.CallMethodStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.CodeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ComparisonContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.DivContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ElseIfStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ElseStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ExitwhenStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.IfStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InitContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LocalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LogicalContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LoopStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MinusContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MultContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NegativeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NotContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NullContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParenthesisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PlusContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PropertyStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.RealContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StringContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StructDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StructStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.SuperThistypeThisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ThisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ThisExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.TopDeclarationContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.VariableExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ChainExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionOrVariableContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.IntegerContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MethodDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ReturnStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.SetVariableStatementContext;
import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LibrarySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.InitializerHandler;
import com.ruke.vrjassc.vrjassc.util.TokenSymbolBag;

public class TranslationPhase extends vrjassBaseVisitor<Expression> {
	
	protected TokenSymbolBag symbols;
	
	protected InitializerHandler initializerHandler;
	
	protected JassContainer container;

	private int classEnum;
	
	private int propertyEnum;
	
	public TranslationPhase(TokenSymbolBag symbols) {
		this.symbols = symbols;
	}

	@Override
	public Expression visitInit(InitContext ctx) {
		this.container = new JassContainer();
		this.initializerHandler = new InitializerHandler();
		
		InitializerList initList = new InitializerList(this.initializerHandler);
		Expression e;
		
		for (TopDeclarationContext stat : ctx.topDeclaration()) {
			e = this.visit(stat);
			
			if (e!= null) {
				if (e.getSymbol().getName().equals("main")) {
					((StatementBody) e).add(initList);
				}
				
				this.container.add((Statement) e);
			}
		}
		
		if (this.classEnum > 0) {
			Symbol symbol = new Symbol("vrjass_structs", null, null);
			symbol.setType(new BuiltInTypeSymbol("hashtable", null, null));
			
			Statement structHashtable = new VariableStatement(symbol, new RawExpression("InitHashtable()"));
			this.container.addGlobal(structHashtable);
		}

		return this.container;
	}

	@Override
	public Expression visitNull(NullContext ctx) {
		return new RawExpression("null");
	}

	@Override
	public Expression visitLogical(LogicalContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitString(StringContext ctx) {
		return new RawExpression(ctx.getText());
	}

	@Override
	public Expression visitCode(CodeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitInteger(IntegerContext ctx) {
		return new RawExpression(ctx.getText());
	}

	@Override
	public Expression visitDiv(DivContext ctx) {
		Expression a = this.visit(ctx.left);
		Expression b = this.visit(ctx.right);
		
		return new MathExpression(a, MathExpression.Operator.DIV, b);
	}

	@Override
	public Expression visitNot(NotContext ctx) {
		return null;
	}

	@Override
	public Expression visitParenthesis(ParenthesisContext ctx) {
		return new ParenthesisExpression(this.visit(ctx.expression()));
	}

	@Override
	public Expression visitNegative(NegativeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitMult(MultContext ctx) {
		Expression a = this.visit(ctx.left);
		Expression b = this.visit(ctx.right);
		
		return new MathExpression(a, MathExpression.Operator.MULT, b);
	}

	@Override
	public Expression visitComparison(ComparisonContext ctx) {
		Expression a = this.visit(ctx.left);
		Expression b = null;
		BooleanExpression.Operator operator = null;
		
		if (ctx.right != null) {
			b = this.visit(ctx.right);
		}
		
		if (ctx.EQEQ() != null) {
			operator = BooleanExpression.Operator.EQUAL_EQUAL;
		} else if (ctx.NOT_EQ() != null) {
			operator = BooleanExpression.Operator.NOT_EQUAL;
		} else if (ctx.GREATER() != null) {
			operator = BooleanExpression.Operator.GREATER;
		} else if (ctx.GREATER_EQ() != null) {
			operator = BooleanExpression.Operator.GREATER_EQUAL;
		} else if (ctx.LESS() != null) {
			operator = BooleanExpression.Operator.LESS;
		} else if (ctx.LESS_EQ() != null) {
			operator = BooleanExpression.Operator.LESS_EQUAL;
		}
		
		return new BooleanExpression(a, operator, b);
	}

	@Override
	public Expression visitReal(RealContext ctx) {
		return new RawExpression(ctx.getText());
	}

	@Override
	public Expression visitThis(ThisContext ctx) {
		Symbol symbol = this.symbols.getVariable(ctx);
		return new VariableExpression(symbol, null);
	}

	@Override
	public Expression visitBoolean(BooleanContext ctx) {
		return new RawExpression(ctx.getText());
	}

	@Override
	public Expression visitPlus(PlusContext ctx) {
		Expression a = this.visit(ctx.left);
		Expression b = this.visit(ctx.right);
		
		return new MathExpression(a, MathExpression.Operator.PLUS, b);
	}

	@Override
	public Expression visitMinus(MinusContext ctx) {
		Expression a = this.visit(ctx.left);
		Expression b = this.visit(ctx.right);
		
		return new MathExpression(a, MathExpression.Operator.MINUS, b);
	}

	@Override
	public Expression visitSuperThistypeThis(SuperThistypeThisContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitFunctionExpression(FunctionExpressionContext ctx) {
		Symbol symbol = this.symbols.getFunction(ctx);
		ExpressionList args = null;
		
		if (ctx.arguments() == null) {
			args = new ExpressionList();
		} else {
			args = (ExpressionList) this.visit(ctx.arguments());
		}
		
		return new FunctionExpression(symbol, false, args);
	}

	@Override
	public Expression visitVariableExpression(VariableExpressionContext ctx) {
		Symbol symbol = this.symbols.getVariable(ctx);
		Expression index = null;
		
		if (ctx.index != null) {
			index = this.visit(ctx.index);
		}
		
		return new VariableExpression(symbol, index);
	}
	
	@Override
	public Expression visitBooleanExpression(BooleanExpressionContext ctx) {
		return new BooleanExpression(this.visit(ctx.expression()));
	}

	@Override
	public Expression visitThisExpression(ThisExpressionContext ctx) {
		Symbol symbol = this.symbols.getVariable(ctx);
		return new VariableExpression(symbol, null);
	}

	@Override
	public Expression visitChainExpression(ChainExpressionContext ctx) {
		ChainExpression chain = new ChainExpression();
		
		chain.setHashtableName("vrjass_structs");
		
		for (FunctionOrVariableContext expr : ctx.functionOrVariable()) {
			chain.append(this.visit(expr), null);
		}
		
		return chain;
	}

	@Override
	public Expression visitElseIfStatement(ElseIfStatementContext ctx) {
		ElseIfStatement elif = new ElseIfStatement(this.visit(ctx.booleanExpression()));
		
		for (StatementContext statement : ctx.statements().statement()) {
			elif.add((Statement) this.visit(statement));
		}
		
		return elif;
	}

	@Override
	public Expression visitElseStatement(ElseStatementContext ctx) {
		ElseStatement _else = new ElseStatement();
		
		for (StatementContext statement : ctx.statements().statement()) {
			_else.add((Statement) this.visit(statement));
		}
		
		return _else;
	}

	@Override
	public Expression visitIfStatement(IfStatementContext ctx) {
		IfStatement _if = new IfStatement(this.visit(ctx.booleanExpression()));

		for (StatementContext stat : ctx.statements().statement()) {
			_if.add((Statement) this.visit(stat));
		}
		
		return _if;
	}

	@Override
	public Expression visitLoopStatement(LoopStatementContext ctx) {
		LoopStatement loop = new LoopStatement();
		
		for (StatementContext statement : ctx.statements().statement()) {
			loop.add((Statement) this.visit(statement));
		}
		
		return loop;
	}

	@Override
	public Expression visitGlobalVariableStatement(GlobalVariableStatementContext ctx) {
		Symbol variable = this.symbols.getGlobalVariable(ctx);
		
		Statement global;
		Expression value = null;
		
		if (ctx.value != null) {
			value = this.visit(ctx.value);
		}
		
		global = new VariableStatement(variable, value);
		this.container.addGlobal(global);
		
		return global;
	}

	@Override
	public Expression visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		Symbol symbol = this.symbols.getVariable(ctx);
		Expression value = null;
		
		if (ctx.value != null) {
			value = this.visit(ctx.value);
		}
		
		return new VariableStatement(symbol, value);
	}

	@Override
	public Expression visitSetVariableStatement(SetVariableStatementContext ctx) {
		Expression name = this.visit(ctx.name);
		Operator operator = null;
		Expression value;
		
		if (ctx.PLUS() != null) {
			operator = Operator.PLUS;
		} else if (ctx.MINUS() != null) {
			operator = Operator.MINUS;
		} else if (ctx.TIMES() != null) {
			operator = Operator.MULT;
		} else if (ctx.DIV() != null) {
			operator = Operator.DIV;
		}
		
		value = this.visit(ctx.value);
		
		if (operator != null) {
			value = new MathExpression(name, operator, value);
		}
		
		return new AssignmentStatement(name, value);
	}

	@Override
	public Expression visitCallMethodStatement(CallMethodStatementContext ctx) {
		Expression method = this.visit(ctx.memberExpression());
		return new FunctionStatement(method);
	}

	@Override
	public Expression visitCallFunctionStatement(CallFunctionStatementContext ctx) {
		return new FunctionStatement(this.visit(ctx.functionExpression()));
	}

	@Override
	public Expression visitExitwhenStatement(ExitwhenStatementContext ctx) {
		return new ExitWhenStatement(this.visit(ctx.booleanExpression()));
	}

	@Override
	public Expression visitReturnStatement(ReturnStatementContext ctx) {
		Expression value = null;
		
		if (ctx.expression() != null) {
			value = this.visit(ctx.expression());
		}
		
		return new ReturnStatement(value);
	}
	
	@Override
	public Expression visitStructDefinition(StructDefinitionContext ctx) {
		Statement statement;
		
		this.classEnum++;
		
		for (StructStatementContext ssc : ctx.structStatements().structStatement()) {
			statement = (Statement) this.visit(ssc);
			
			if (statement != null) {
				this.container.add(statement);
			}
		}
		
		return null;
	}

	@Override
	public Expression visitMethodDefinition(MethodDefinitionContext ctx) {
		FunctionSymbol symbol = (FunctionSymbol) this.symbols.getMethod(ctx);
		StatementBody method = new FunctionDefinition(symbol);
		
		for (StatementContext statement : ctx.statements().statement()) {
			method.add((Statement) this.visit(statement));
		}
		
		return method;
	}

	@Override
	public Expression visitFunctionDefinition(FunctionDefinitionContext ctx) {
		FunctionSymbol symbol = (FunctionSymbol) this.symbols.getFunction(ctx);
		StatementBody function = new FunctionDefinition(symbol);
		
		for (StatementContext statement : ctx.statements().statement()) {
			function.add((Statement) this.visit(statement));
		}
		
		return function;
	}

	@Override
	public Expression visitArguments(ArgumentsContext ctx) {
		ExpressionList args = new ExpressionList();
		
		for (ExpressionContext expr : ctx.expression()) {
			args.add(this.visit(expr));
		}
		
		return args;
	}
	
	@Override
	public Expression visitPropertyStatement(PropertyStatementContext ctx) {
		this.propertyEnum++;
		
		Expression value = new RawExpression(String.valueOf(this.propertyEnum));
		Symbol symbol = this.symbols.getProperty(ctx);
		
		Statement global = new VariableStatement(symbol, value);
		
		this.container.addGlobal(global);
		
		return null;
	}
		
	@Override
	public Expression visitLibraryDefinition(LibraryDefinitionContext ctx) {
		LibrarySymbol lib = (LibrarySymbol) this.symbols.getLibrary(ctx);
		Expression e;
		
		this.initializerHandler.add(lib);
		
		for (LibraryStatementContext statement : ctx.libraryStatements().libraryStatement()) {
			e = this.visit(statement);
			
			if (e != null) {
				this.container.add((Statement) e);
			}
		}
		
		return null;
	}
	
}
