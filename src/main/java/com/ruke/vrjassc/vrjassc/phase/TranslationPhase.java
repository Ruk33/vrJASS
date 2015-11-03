package com.ruke.vrjassc.vrjassc.phase;

import java.util.Hashtable;
import java.util.Stack;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

import com.ruke.vrjassc.translator.DefaultPropertyValueTranslator;
import com.ruke.vrjassc.translator.expression.BooleanExpression;
import com.ruke.vrjassc.translator.expression.Expression;
import com.ruke.vrjassc.translator.expression.MathExpression;
import com.ruke.vrjassc.translator.expression.ParenthesisExpression;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.ReturnStatement;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ArgumentsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.BooleanContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.BooleanExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.CallFunctionStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.CallMethodStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.CodeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ComparisonContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.DivContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ElseIfStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ElseIfStatementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ElseStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ExitwhenStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ExtendValidNameContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalStatementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.IfStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ImplementsListContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InitContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.IntegerExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InterfaceDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InterfaceStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InterfaceStatementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryInitializerContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryRequirementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryRequirementsListContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryStatementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LocalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LogicalContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LoopStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MemberContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MemberExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MinusContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MultContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NativeDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NegativeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NotContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NullContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParametersContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParenthesisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PlusContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PublicPrivateContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.RealContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StatementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StringContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StructStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StructStatementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.SuperThistypeThisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ThisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ThisExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.TopDeclarationContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.TypeDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ValidImplementNameContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ValidNameContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ValidTypeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.VariableContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.VariableExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.VisibilityContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ChainExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionOrVariableContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.IntegerContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MethodDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParameterContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PropertyStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ReturnStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ReturnTypeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.SetVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StructDefinitionContext;
import com.ruke.vrjassc.vrjassc.symbol.Scope;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.TokenSymbolBag;

public class TranslationPhase extends AbstractParseTreeVisitor<Expression> implements vrjassVisitor<Expression> {

	protected TokenSymbolBag symbols;
	
	protected StringBuilder globals;
	protected StringBuilder output;
	
	protected int classEnum;
	protected int propertyEnum;
	protected String propertyValue;
	
	protected Hashtable<Symbol, String> propertiesKey = new Hashtable<Symbol, String>();
	
	protected Hashtable<Scope, Stack<Symbol>> propertiesWithDefaultValue = new Hashtable<Scope, Stack<Symbol>>();
	protected DefaultPropertyValueTranslator propertiesDefaultValue = new DefaultPropertyValueTranslator();
	
	public TranslationPhase(TokenSymbolBag symbols, Scope scope) {
		this.output = new StringBuilder();
		this.globals = new StringBuilder();
		
		this.symbols = symbols;
	}

	@Override
	public Expression visitInit(InitContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Expression visitTopDeclaration(TopDeclarationContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Expression visitNull(NullContext ctx) {
		return new RawExpression("null");
	}

	@Override
	public Expression visitVariable(VariableContext ctx) {
		// TODO Auto-generated method stub
		return null;
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
	public Expression visitFunction(FunctionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitNot(NotContext ctx) {
		// TODO Auto-generated method stub
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
		Expression b = this.visit(ctx.right);
		BooleanExpression.Operator operator;
		
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
		} else {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitMember(MemberContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitMinus(MinusContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitSuperThistypeThis(SuperThistypeThisContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitFunctionExpression(FunctionExpressionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitVariableExpression(VariableExpressionContext ctx) {
		// TODO Auto-generated method stub
		return null;
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
	public Expression visitIntegerExpression(IntegerExpressionContext ctx) {
		return this.visit(ctx.expression());
	}

	@Override
	public Expression visitBooleanExpression(BooleanExpressionContext ctx) {
		return this.visit(ctx.expression());
	}

	@Override
	public Expression visitStatements(StatementsContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Expression visitElseIfStatement(ElseIfStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitElseIfStatements(ElseIfStatementsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitElseStatement(ElseStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitIfStatement(IfStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitLoopStatement(LoopStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitPublicPrivate(PublicPrivateContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitGlobalVariableStatement(GlobalVariableStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitSetVariableStatement(SetVariableStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitCallMethodStatement(CallMethodStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitCallFunctionStatement(CallFunctionStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitExitwhenStatement(ExitwhenStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitReturnStatement(ReturnStatementContext ctx) {
		return new ReturnStatement(this.visit(ctx));
	}

	@Override
	public Expression visitStatement(StatementContext ctx) {
		return visitChildren(ctx);
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
	public Expression visitTypeDefinition(TypeDefinitionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitNativeDefinition(NativeDefinitionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitPropertyStatement(PropertyStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitStructStatement(StructStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitStructStatements(StructStatementsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitValidImplementName(ValidImplementNameContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Expression visitImplementsList(ImplementsListContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitExtendValidName(ExtendValidNameContext ctx) {
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
	public Expression visitInterfaceStatement(InterfaceStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitInterfaceStatements(InterfaceStatementsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitInterfaceDefinition(InterfaceDefinitionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitFunctionDefinition(FunctionDefinitionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitLibraryInitializer(LibraryInitializerContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitLibraryRequirementsList(LibraryRequirementsListContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitLibraryRequirements(LibraryRequirementsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitLibraryStatement(LibraryStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitLibraryStatements(LibraryStatementsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitLibraryDefinition(LibraryDefinitionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitReturnType(ReturnTypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitParameter(ParameterContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitParameters(ParametersContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitArguments(ArgumentsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitValidType(ValidTypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitValidName(ValidNameContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visitVisibility(VisibilityContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
