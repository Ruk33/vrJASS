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
import com.ruke.vrjassc.translator.expression.LogicalExpression;
import com.ruke.vrjassc.translator.expression.LoopStatement;
import com.ruke.vrjassc.translator.expression.MathExpression;
import com.ruke.vrjassc.translator.expression.MathExpression.Operator;
import com.ruke.vrjassc.translator.expression.NegativeExpression;
import com.ruke.vrjassc.translator.expression.ParenthesisExpression;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.ReturnStatement;
import com.ruke.vrjassc.translator.expression.Statement;
import com.ruke.vrjassc.translator.expression.StatementBody;
import com.ruke.vrjassc.translator.expression.VariableExpression;
import com.ruke.vrjassc.translator.expression.VariableStatement;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ArgumentsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.AssignmentStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.BooleanContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.CastContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.CodeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ComparisonContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.DivContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ElseIfStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ElseStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ExitWhenStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionStatementContext;
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
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ThisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.TopDeclarationContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.VariableExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ChainExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.IntegerContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ReturnStatementContext;
import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LibrarySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.ScopeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.InitializerHandler;
import com.ruke.vrjassc.vrjassc.util.TokenSymbolBag;

public class TranslationPhase extends vrjassBaseVisitor<Expression> {
	
	protected TokenSymbolBag symbols;
	
	protected ScopeSymbol scope;
	
	protected InitializerHandler initializerHandler;
	
	protected JassContainer container;

	private int classEnum;
	
	private int propertyEnum;
	
	public TranslationPhase(TokenSymbolBag symbols, ScopeSymbol scope) {
		this.symbols = symbols;
		this.scope = scope;
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
			
			Statement structHashtable = new VariableStatement(symbol, new RawExpression("InitHashtable()", null));
			this.container.addGlobal(structHashtable);
		}

		return this.container;
	}
	
	@Override
	public Expression visitCast(CastContext ctx) {
		Expression casted = this.visit(ctx.expression());
		Symbol symbol = casted.getSymbol();
		
		if (symbol instanceof BuiltInTypeSymbol) {
			symbol = null;
		}
		
		return new RawExpression(casted.translate(), symbol);
	}

	@Override
	public Expression visitNull(NullContext ctx) {
		return new RawExpression("null", this.scope.resolve("null"));
	}

	@Override
	public Expression visitLogical(LogicalContext ctx) {
		Expression left = new BooleanExpression(this.visit(ctx.left));
		Expression right = new BooleanExpression(this.visit(ctx.right));
		
		LogicalExpression.Operator operator;
		
		if (ctx.OR() == null) {
			operator = LogicalExpression.Operator.AND;
		} else {
			operator = LogicalExpression.Operator.OR;
		}

		return new LogicalExpression(left, operator, right);
	}

	@Override
	public Expression visitString(StringContext ctx) {
		return new RawExpression(ctx.getText(), this.scope.resolve("string"));
	}

	@Override
	public Expression visitCode(CodeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitInteger(IntegerContext ctx) {
		return new RawExpression(ctx.getText(), this.scope.resolve("integer"));
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
		return new NegativeExpression(this.visit(ctx.expression()));
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
		return new RawExpression(ctx.getText(), this.scope.resolve("real"));
	}

	@Override
	public Expression visitBoolean(BooleanContext ctx) {
		return new RawExpression(ctx.getText(), this.scope.resolve("boolean"));
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
	public Expression visitFunctionExpression(FunctionExpressionContext ctx) {
		Symbol symbol = this.symbols.get(ctx);
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
		Symbol symbol = this.symbols.get(ctx);
		Expression index = null;
		
		if (ctx.index != null) {
			index = this.visit(ctx.index);
		}
		
		return new VariableExpression(symbol, index);
	}

	@Override
	public Expression visitThis(ThisContext ctx) {
		return new VariableExpression(this.symbols.get(ctx), null);
	}
	
	@Override
	public Expression visitChainExpression(ChainExpressionContext ctx) {
		ChainExpression chain = new ChainExpression();
		
		chain.setHashtableName("vrjass_structs");
		
		for (ExpressionContext expr : ctx.expression()) {
			Expression e = this.visit(expr);
			chain.append(e, null);
		}
		
		return chain;
	}

	@Override
	public Expression visitElseIfStatement(ElseIfStatementContext ctx) {
		BooleanExpression condition = new BooleanExpression(this.visit(ctx.expression()));
		ElseIfStatement elif = new ElseIfStatement(condition);
		
		for (StatementContext statement : ctx.statement()) {
			elif.add((Statement) this.visit(statement));
		}
		
		return elif;
	}

	@Override
	public Expression visitElseStatement(ElseStatementContext ctx) {
		ElseStatement _else = new ElseStatement();
		
		for (StatementContext statement : ctx.statement()) {
			_else.add((Statement) this.visit(statement));
		}
		
		return _else;
	}

	@Override
	public Expression visitIfStatement(IfStatementContext ctx) {
		BooleanExpression condition = new BooleanExpression(this.visit(ctx.expression()));
		IfStatement _if = new IfStatement(condition);

		for (StatementContext stat : ctx.statement()) {
			_if.add((Statement) this.visit(stat));
		}
		
		return _if;
	}

	@Override
	public Expression visitLoopStatement(LoopStatementContext ctx) {
		LoopStatement loop = new LoopStatement();
		
		for (StatementContext statement : ctx.statement()) {
			loop.add((Statement) this.visit(statement));
		}
		
		return loop;
	}
	
	@Override
	public Expression visitGlobalVariableStatement(GlobalVariableStatementContext ctx) {
		Symbol variable = this.symbols.get(ctx);
		Expression value = null;
		
		if (ctx.variableDeclaration().value != null) {
			value = this.visit(ctx.variableDeclaration().value);
		}
		
		Statement global = new VariableStatement(variable, value);
		this.container.addGlobal(global);
		
		return global;
	}

	@Override
	public Expression visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		Symbol symbol = this.symbols.get(ctx);
		Expression value = null;
		
		if (ctx.variableDeclaration().value != null) {
			value = this.visit(ctx.variableDeclaration().value);
		}
		
		return new VariableStatement(symbol, value);
	}

	@Override
	public Expression visitAssignmentStatement(AssignmentStatementContext ctx) {
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
			// avoid mutual recursion
			value = new MathExpression(new RawExpression(name.translate(), name.getSymbol()), operator, value);
		}
		
		return new AssignmentStatement(name, value);
	}

	@Override
	public Expression visitFunctionStatement(FunctionStatementContext ctx) {
		return new FunctionStatement(this.visit(ctx.expression()));
	}

	@Override
	public Expression visitExitWhenStatement(ExitWhenStatementContext ctx) {
		BooleanExpression condition = new BooleanExpression(this.visit(ctx.expression()));
		return new ExitWhenStatement(condition);
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
		
		ClassSymbol _class = (ClassSymbol) this.symbols.get(ctx);
		this.initializerHandler.add(_class);
		
		for (StructStatementContext ssc : ctx.structStatement()) {
			statement = (Statement) this.visit(ssc);
			
			if (statement != null) {
				this.container.add(statement);
			}
		}
		
		return null;
	}

	@Override
	public Expression visitFunctionDefinition(FunctionDefinitionContext ctx) {
		FunctionSymbol symbol = (FunctionSymbol) this.symbols.get(ctx);
		StatementBody function = new FunctionDefinition(symbol);
		Expression e;
		
		for (StatementContext statement : ctx.statement()) {
			e = this.visit(statement);
			
			if (e != null) {
				function.add((Statement) e);
			}
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
		Symbol symbol = this.symbols.get(ctx);
		Expression value = null;
		Statement global;
		
		if (!symbol.hasModifier(Modifier.STATIC)) {
			this.propertyEnum++;
			value = new RawExpression(String.valueOf(this.propertyEnum), this.scope.resolve("integer"));
		}
		
		global = new VariableStatement(symbol, value);
		this.container.addGlobal(global);
		
		return null;
	}
		
	@Override
	public Expression visitLibraryDefinition(LibraryDefinitionContext ctx) {
		LibrarySymbol lib = (LibrarySymbol) this.symbols.get(ctx);
		Expression e;
		
		this.initializerHandler.add(lib);
		
		for (LibraryStatementContext statement : ctx.libraryStatement()) {
			e = this.visit(statement);
			
			if (e != null) {
				this.container.add((Statement) e);
			}
		}
		
		return null;
	}
	
}
