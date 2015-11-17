package com.ruke.vrjassc.vrjassc.phase;

import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.AssignmentStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.BooleanContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.CastContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ChainExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.CodeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ComparisonContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.DivContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ExitWhenStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.IntegerContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InterfaceDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InterfaceStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LogicalContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MinusContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MultContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NativeDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NegativeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NotContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NullContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParameterContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParametersContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParenthesisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PlusContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.RealContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ReturnStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StringContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StructDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StructStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ThisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.TypeDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ValidNameContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.VariableDeclarationContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.VariableExpressionContext;
import com.ruke.vrjassc.vrjassc.exception.InvalidStatementException;
import com.ruke.vrjassc.vrjassc.exception.NoFunctionException;
import com.ruke.vrjassc.vrjassc.symbol.CastSymbol;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.InitTrigSymbol;
import com.ruke.vrjassc.vrjassc.symbol.InterfaceSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LibrarySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Scope;
import com.ruke.vrjassc.vrjassc.symbol.ScopeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;
import com.ruke.vrjassc.vrjassc.util.TokenSymbolBag;
import com.ruke.vrjassc.vrjassc.util.Validator;

public class ReferencePhase extends vrjassBaseVisitor<Symbol> {

	private TokenSymbolBag symbols;
	
	private Validator validator;
	
	private ScopeSymbol scope;
	
	private Stack<ScopeSymbol> scopes;
			
	public ReferencePhase(TokenSymbolBag symbols, ScopeSymbol scope) {
		this.symbols = symbols;
		this.validator = new Validator();
		this.scope = scope;
		this.scopes = new Stack<ScopeSymbol>();
		
		this.scopes.push(this.scope);
	}
	
	@Override
	public Symbol visitTypeDefinition(TypeDefinitionContext ctx) {
		return this.symbols.get(ctx);
	}
	
	@Override
	public Symbol visitNativeDefinition(NativeDefinitionContext ctx) {
		return this.symbols.get(ctx);
	}
	
	@Override
	public Symbol visitLibraryDefinition(LibraryDefinitionContext ctx) {
		LibrarySymbol library = (LibrarySymbol) this.symbols.get(ctx);
		
		this.scopes.push(library);
		
		if (ctx.libInit != null) {
			String initName = ctx.libInit.getText();
			Token initToken = ctx.libInit.getStart();
			
			if (!this.validator.mustBeDefined(library, initName, initToken)) {
				throw this.validator.getException();
			}
			
			library.setInitializer(this.validator.getValidatedSymbol());
		}
		
		if (ctx.libraryRequirements() != null) {
			String reqName;
			Token reqToken;
			
			for (ValidNameContext requirement : ctx.libraryRequirements().validName()) {
				reqName = requirement.getText();
				reqToken = requirement.getStart();
				
				if (!this.validator.mustBeDefined(library, reqName, reqToken)) {
					throw this.validator.getException();
				}
				
				library.defineRequirement((LibrarySymbol) this.validator.getValidatedSymbol());
			}
		}
		
		super.visitLibraryDefinition(ctx);
		
		this.scopes.pop();
		
		return library;
	}
	
	@Override
	public Symbol visitInterfaceDefinition(InterfaceDefinitionContext ctx) {
		InterfaceSymbol _interface = (InterfaceSymbol) this.symbols.get(ctx);
		
		this.scopes.push(_interface);
		super.visitInterfaceDefinition(ctx);
		this.scopes.pop();
		
		return _interface;
	}
	
	@Override
	public Symbol visitInterfaceStatement(InterfaceStatementContext ctx) {
		FunctionSymbol function = (FunctionSymbol) this.symbols.get(ctx);
		
		this.scopes.push(function);
		
		super.visitInterfaceStatement(ctx);
		
		if (ctx.returnType().validType() != null) {
			String type = ctx.returnType().validType().getText();
			Token token = ctx.returnType().getStart();
			
			if (!this.validator.mustBeDefined(function, type, token)) {
				throw this.validator.getException();
			}
			
			if (!this.validator.mustBeValidType(this.validator.getValidatedSymbol(), token)) {
				throw this.validator.getException();
			}
			
			if (!this.validator.mustHaveAccess(function, this.validator.getValidatedSymbol(), token)) {
				throw this.validator.getException();
			}
			
			function.setType((Type) this.validator.getValidatedSymbol());
		}
		
		this.scopes.pop();
		
		return function;
	}
	
	@Override
	public Symbol visitStructDefinition(StructDefinitionContext ctx) {		
		ClassSymbol _class = (ClassSymbol) this.symbols.get(ctx);
		
		this.scopes.push(_class);
		
		if (ctx.extendsFrom != null) {
			String extendsName = ctx.extendsFrom.getText();
			Token extendsToken = ctx.extendsFrom.getStart();
			
			if (!this.validator.mustBeDefined(_class, extendsName, extendsToken)) {
				throw this.validator.getException();
			}
			
			if (!this.validator.mustBeExtendableValid(this.validator.getValidatedSymbol(), extendsToken)) {
				throw this.validator.getException();
			}
			
			_class.extendsFrom((ClassSymbol) this.validator.getValidatedSymbol());
		}
		
		if (ctx.implementsList() != null) {
			String interfaceName;
			Token interfaceToken;
			
			for (ValidNameContext implement : ctx.implementsList().validName()) {
				interfaceName = implement.getText();
				interfaceToken = implement.getStart();
				
				if (!this.validator.mustBeDefined(_class, interfaceName, interfaceToken)) {
					throw this.validator.getException();
				}
				
				if (!this.validator.mustBeImplementableTypeValid(this.validator.getValidatedSymbol(), interfaceToken)) {
					throw this.validator.getException();
				}
				
				_class.define(this.validator.getValidatedSymbol());
			}
		}
		
		super.visitStructDefinition(ctx);
		
		this.scopes.pop();
		
		return _class;
	}
	
	@Override
	public Symbol visitFunctionDefinition(FunctionDefinitionContext ctx) {		
		FunctionSymbol function = (FunctionSymbol) this.symbols.get(ctx);
		
		this.scopes.push(function);
		
		if (ctx.returnType().validType() != null) {
			String type = ctx.returnType().validType().getText();
			Token token = ctx.returnType().getStart();
			
			if (!this.validator.mustBeDefined(function, type, token)) {
				throw this.validator.getException();
			}
			
			if (!this.validator.mustBeValidType(this.validator.getValidatedSymbol(), token)) {
				throw this.validator.getException();
			}
			
			if (!this.validator.mustHaveAccess(function, this.validator.getValidatedSymbol(), token)) {
				throw this.validator.getException();
			}
			
			function.setType((Type) this.validator.getValidatedSymbol());
			
			if (!this.validator.mustReturn(function, ctx.statement())) {
				throw this.validator.getException();
			}
		}
		
		super.visitFunctionDefinition(ctx);
		
		this.scopes.pop();
		
		return function;
	}
	
	@Override
	public Symbol visitVariableDeclaration(VariableDeclarationContext ctx) {
		String typeName = ctx.validType().getText();
		Token typeToken = ctx.validType().getStart();
		
		Symbol variable = this.symbols.get(ctx);
		Scope scope = this.scopes.peek();

		if (!this.validator.mustBeDefined(scope, typeName, typeToken)) {
			throw this.validator.getException();
		}
		
		if (!this.validator.mustBeValidType(this.validator.getValidatedSymbol(), typeToken)) {
			throw this.validator.getException();
		}
		
		if (!this.validator.mustHaveAccess(scope, this.validator.getValidatedSymbol(), typeToken)) {
			throw this.validator.getException();
		}
		
		variable.setType((Type) this.validator.getValidatedSymbol());
		
		if (ctx.value != null) {
			Symbol value = this.visit(ctx.value);
			
			if (!this.validator.mustBeTypeCompatible(variable, value, typeToken)) {
				throw this.validator.getException();
			}
		}
		
		return variable;
	}
	
	@Override
	public Symbol visitDiv(DivContext ctx) {
		Symbol left = this.visit(ctx.left);
		Symbol right = this.visit(ctx.right);
		
		if (!this.validator.mustBeValidMathOperation(left, right, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return this.scope.resolve("real");
	}
	
	@Override
	public Symbol visitMult(MultContext ctx) {
		Symbol left = this.visit(ctx.left);
		Symbol right = this.visit(ctx.right);
		
		if (!this.validator.mustBeValidMathOperation(left, right, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return this.scope.resolve("real");
	}
	
	@Override
	public Symbol visitPlus(PlusContext ctx) {
		Symbol left = this.visit(ctx.left);
		Symbol right = this.visit(ctx.right);
		
		if (left.getType().getName().equals("string")) {
			if (!this.validator.mustBeValidStringConcatenation(left, right, ctx.getStart())) {
				throw this.validator.getException();
			}
		} else {
			if (!this.validator.mustBeValidMathOperation(left, right, ctx.getStart())) {
				throw this.validator.getException();
			}
		}
		
		return left;
	}
	
	@Override
	public Symbol visitMinus(MinusContext ctx) {
		Symbol left = this.visit(ctx.left);
		Symbol right = this.visit(ctx.right);
		
		if (!this.validator.mustBeValidMathOperation(left, right, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return this.scope.resolve("real");
	}
	
	@Override
	public Symbol visitComparison(ComparisonContext ctx) {
		super.visitComparison(ctx);
		return this.scope.resolve("boolean");
	}
	
	@Override
	public Symbol visitLogical(LogicalContext ctx) {
		super.visitLogical(ctx);
		return this.scope.resolve("boolean");
	}
	
	@Override
	public Symbol visitInteger(IntegerContext ctx) {
		return this.scope.resolve("integer");
	}
	
	@Override
	public Symbol visitReal(RealContext ctx) {
		return this.scope.resolve("real");
	}
	
	@Override
	public Symbol visitString(StringContext ctx) {
		return this.scope.resolve("string");
	}
	
	@Override
	public Symbol visitNegative(NegativeContext ctx) {
		return this.visit(ctx.expression());
	}
	
	@Override
	public Symbol visitNot(NotContext ctx) {
		this.visit(ctx.expression());
		return this.scope.resolve("boolean");
	}
	
	@Override
	public Symbol visitBoolean(BooleanContext ctx) {
		return this.scope.resolve("boolean");
	}
	
	@Override
	public Symbol visitNull(NullContext ctx) {
		return this.scope.resolve("null");
	}
	
	@Override
	public Symbol visitCode(CodeContext ctx) {
		this.visit(ctx.expression());
		return this.scope.resolve("code");
	}
	
	@Override
	public Symbol visitThis(ThisContext ctx) {
		return this.scopes.peek().resolve("this");
	}
	
	@Override
	public Symbol visitFunctionExpression(FunctionExpressionContext ctx) {
		Scope scope = this.scopes.peek();
		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		if (!this.validator.mustBeDefined(scope, name, token)) {
			if (name.startsWith("InitTrig_")) {
				scope.getEnclosingScope().define(new InitTrigSymbol(name, null));
				this.validator.mustBeDefined(scope, name, token);
			} else {
				throw this.validator.getException();
			}
		}
		
		if (this.validator.getValidatedSymbol() instanceof FunctionSymbol == false) {
			throw new NoFunctionException(token, this.validator.getValidatedSymbol());
		}
		
		FunctionSymbol function = (FunctionSymbol) this.validator.getValidatedSymbol();
		Stack<Symbol> arguments = new Stack<Symbol>();
		
		if (ctx.arguments() != null) {
			for (ExpressionContext expr : ctx.arguments().expression()) {
				arguments.push(this.visit(expr));
			}
		}
		
		if (!this.validator.mustMatchArguments(function, arguments, token)) {
			throw this.validator.getException();
		}
		
		this.symbols.put(ctx, function);
		
		return function;
	}
	
	@Override
	public Symbol visitVariableExpression(VariableExpressionContext ctx) {
		Scope scope = this.scopes.peek();
		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		if (!this.validator.mustBeDefined(scope, name, token)) {
			throw this.validator.getException();
		}
		
		Symbol variable = this.validator.getValidatedSymbol();
		
		if (!this.validator.mustBeDeclaredBeforeUsed(variable, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		if (ctx.index != null) {
			this.visit(ctx.index);
		}
		
		this.symbols.put(ctx, variable);
		
		return variable;
	}
	
	@Override
	public Symbol visitCast(CastContext ctx) {
		String castType = ctx.validName().getText();
		
		Symbol original = this.visit(ctx.expression());
		Symbol cast = original.getParentScope().resolve(castType);
		
		return new CastSymbol(original, cast, ctx.getStart());
	}
	
	@Override
	public Symbol visitParenthesis(ParenthesisContext ctx) {
		Symbol symbol = this.visit(ctx.expression());
		this.symbols.put(ctx, symbol);
		return symbol;
	}
	
	@Override
	public Symbol visitChainExpression(ChainExpressionContext ctx) {
		Scope scope = this.scopes.peek();
		
		Symbol prevSymbol = null;
		Symbol symbol = null;
		int pushed = 0;
		
		for (ExpressionContext expr : ctx.expression()) {
			symbol = this.visit(expr);
			this.symbols.put(expr, symbol);
			
			if (this.validator.mustHaveAccess(scope, symbol, expr.getStart())) {				
				if (symbol.getType() instanceof Scope) { // class
					this.scopes.push((ScopeSymbol) symbol.getType()).toggleEnclosingScope();
					pushed++;
				} else if (symbol instanceof Scope) { // library
					this.scopes.push((ScopeSymbol) symbol).toggleEnclosingScope();
					pushed++;
				} else if (prevSymbol != null) {
					if (!this.validator.mustBeValidMember(prevSymbol, symbol, expr.getStart())) {
						throw this.validator.getException();
					}
				}
			} else {
				throw this.validator.getException();
			}
			
			prevSymbol = symbol;
		}
		
		// if we pushed scopes, come back to the original
		while (pushed != 0) {
			this.scopes.pop().toggleEnclosingScope();
			pushed--;
		}
		
		return symbol;
	}
	
	@Override
	public Symbol visitAssignmentStatement(AssignmentStatementContext ctx) {
		Symbol variable = this.visit(ctx.name);
		Symbol value = this.visit(ctx.value);
		
		if (!this.validator.mustBeTypeCompatible(variable, value, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return variable;
	}
	
	@Override
	public Symbol visitFunctionStatement(FunctionStatementContext ctx) {
		return this.visit(ctx.expression());
	}

	@Override
	public Symbol visitExitWhenStatement(ExitWhenStatementContext ctx) {
		if (!this.validator.mustBeInsideOfLoop(ctx, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return this.visit(ctx.expression());
	}
	
	@Override
	public Symbol visitReturnStatement(ReturnStatementContext ctx) {
		if (ctx.expression() == null) {
			return null;
		}
		
		Scope scope = this.scopes.peek();
		
		Symbol expr = this.visit(ctx.expression());
		Token token = ctx.expression().getStart();
		
		if (!this.validator.mustBeTypeCompatible((Symbol) scope, expr, token)) {
			throw this.validator.getException();
		}
		
		return expr;
	}

}
