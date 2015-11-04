package com.ruke.vrjassc.vrjassc.phase;

import com.ruke.vrjassc.translator.expression.AssignmentStatement;
import com.ruke.vrjassc.translator.expression.BooleanExpression;
import com.ruke.vrjassc.translator.expression.ElseIfStatement;
import com.ruke.vrjassc.translator.expression.ElseStatement;
import com.ruke.vrjassc.translator.expression.ExitWhenStatement;
import com.ruke.vrjassc.translator.expression.Expression;
import com.ruke.vrjassc.translator.expression.ExpressionList;
import com.ruke.vrjassc.translator.expression.FunctionDefinition;
import com.ruke.vrjassc.translator.expression.FunctionExpression;
import com.ruke.vrjassc.translator.expression.FunctionStatement;
import com.ruke.vrjassc.translator.expression.IfStatement;
import com.ruke.vrjassc.translator.expression.JassContainer;
import com.ruke.vrjassc.translator.expression.LoopStatement;
import com.ruke.vrjassc.translator.expression.MathExpression;
import com.ruke.vrjassc.translator.expression.ParenthesisExpression;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.ReturnStatement;
import com.ruke.vrjassc.translator.expression.Statement;
import com.ruke.vrjassc.translator.expression.StatementBody;
import com.ruke.vrjassc.translator.expression.StatementList;
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
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalStatementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.IfStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InitContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LocalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LogicalContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LoopStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MemberContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MemberExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MinusContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MultContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NegativeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NotContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NullContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParenthesisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PlusContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.RealContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StringContext;
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
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StructDefinitionContext;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.TokenSymbolBag;

public class TranslationPhase extends vrjassBaseVisitor<Expression> {

	protected TokenSymbolBag symbols;
		
	public TranslationPhase(TokenSymbolBag symbols) {
		this.symbols = symbols;
	}

	@Override
	public Expression visitInit(InitContext ctx) {
		StatementList jassContainer = new JassContainer();
		
		for (TopDeclarationContext stat : ctx.topDeclaration()) {
			jassContainer.add((Statement) this.visit(stat));
		}

		return jassContainer;
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
		// TODO Auto-generated method stub
		return null;
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
	public Expression visitMember(MemberContext ctx) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitFunctionOrVariable(FunctionOrVariableContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitChainExpression(ChainExpressionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitMemberExpression(MemberExpressionContext ctx) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		return new AssignmentStatement(this.visit(ctx.name), this.visit(ctx.value));
	}

	@Override
	public Expression visitCallMethodStatement(CallMethodStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
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
		return new ReturnStatement(this.visit(ctx));
	}

	@Override
	public Expression visitGlobalStatements(GlobalStatementsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitGlobalDefinition(GlobalDefinitionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitStructDefinition(StructDefinitionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitMethodDefinition(MethodDefinitionContext ctx) {
		// TODO Auto-generated method stub
		return null;
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
	
}
