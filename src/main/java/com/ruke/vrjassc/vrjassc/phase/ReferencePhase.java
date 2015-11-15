package com.ruke.vrjassc.vrjassc.phase;

import java.util.Stack;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.BooleanContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.CastContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ChainExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ExtendValidNameContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.IntegerContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryInitializerContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryRequirementsListContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LocalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MethodDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NativeDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParenthesisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PropertyStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.RealContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ReturnStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ReturnTypeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StringContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StructDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ThisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ThisExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.TypeDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ValidImplementNameContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ValidNameContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.VariableExpressionContext;
import com.ruke.vrjassc.vrjassc.exception.MissReturnException;
import com.ruke.vrjassc.vrjassc.exception.NoFunctionException;
import com.ruke.vrjassc.vrjassc.symbol.CastSymbol;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.InitTrigSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LibrarySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Scope;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;
import com.ruke.vrjassc.vrjassc.util.TokenSymbolBag;
import com.ruke.vrjassc.vrjassc.util.Validator;

public class ReferencePhase extends vrjassBaseVisitor<Symbol> {

	private TokenSymbolBag symbols;
	
	/**
	 * 
	 */
	private Validator validator;
		
	/**
	 * Current working class/struct
	 */
	private ClassSymbol struct;
	
	/**
	 * 
	 */
	private Stack<Scope> scopes;
			
	public ReferencePhase(TokenSymbolBag symbols, Scope scope) {
		this.symbols = symbols;
		this.validator = new Validator();
		this.scopes = new Stack<Scope>();
		
		this.scopes.push(scope);
	}
	
	public Scope getScope() {
		return this.scopes.peek();
	}
	/*
	@Override
	public Symbol visitGlobalVariableStatement(GlobalVariableStatementContext ctx) {
		Symbol global = this.symbols.get(ctx);
		
		global.setType((Type) this.getScope().resolve(ctx.validType().getText()));
		
		return global;
	}
	*/
	@Override
	public Symbol visitCast(CastContext ctx) {
		Symbol original = this.visit(ctx.expression());
		Symbol cast = this.getScope().resolve(ctx.validName().getText());
		
		return new CastSymbol(original, cast, ctx.getStart());
	}
	
	@Override
	public Symbol visitBoolean(BooleanContext ctx) {
		return this.getScope().resolve("boolean");
	}
	
	@Override
	public Symbol visitInteger(IntegerContext ctx) {
		return this.getScope().resolve("integer");
	}
	
	@Override
	public Symbol visitReal(RealContext ctx) {
		return this.getScope().resolve("real");
	}
	
	@Override
	public Symbol visitString(StringContext ctx) {
		return this.getScope().resolve("string");
	}
	
	@Override
	public Symbol visitNativeDefinition(NativeDefinitionContext ctx) {
		return this.getScope().resolve(ctx.validName().getText());
	}
	
	@Override
	public Symbol visitTypeDefinition(TypeDefinitionContext ctx) {
		return this.getScope().resolve(ctx.validName().getText());
	}
	
	@Override
	public Symbol visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		String type = ctx.validType().getText();
		
		Symbol variable = scope.resolve(name);

		if (!this.validator.mustBeDefined(scope, type, ctx.validType().getStart())) {
			throw this.validator.getException();
		}
		
		Symbol variableType = this.validator.getValidatedSymbol();
		
		if (!this.validator.mustBeValidType(variableType, ctx.validType().getStart())) {
			throw this.validator.getException();
		}
		
		variable.setType((Type) variableType);
		
		if (ctx.value != null) {
			Symbol value = this.visit(ctx.value);
			
			if (!this.validator.mustBeTypeCompatible(variable, value, ctx.value.getStart())) {
				throw this.validator.getException();
			}
		}
		
		return variable;
	}
	
	@Override
	public Symbol visitPropertyStatement(PropertyStatementContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		String type = ctx.validType().getText();
		
		Symbol property = scope.resolve(name);

		if (!this.validator.mustBeDefined(scope, type, ctx.validType().getStart())) {
			throw this.validator.getException();
		}
		
		Symbol propertyType = this.validator.getValidatedSymbol();
		
		if (!this.validator.mustBeValidType(propertyType, ctx.validType().getStart())) {
			throw this.validator.getException();
		}
		
		property.setType((Type) propertyType);
		
		return property;
	}
	
	@Override
	public Symbol visitReturnType(ReturnTypeContext ctx) {
		Scope scope = this.getScope();
		String type;
		
		Token typeToken;
		
		if (ctx.NOTHING() == null) {
			typeToken = ctx.validType().getStart();
		} else {
			typeToken = ctx.NOTHING().getSymbol();
		}
		
		type = typeToken.getText();

		if (!this.validator.mustBeDefined(scope, type, typeToken)) {
			throw this.validator.getException();
		}
		
		Symbol resolved = this.validator.getValidatedSymbol();
		
		if (!this.validator.mustBeValidType(resolved, typeToken)) {
			throw this.validator.getException();
		}
		
		((Symbol) scope).setType((Type) resolved);
		
		return resolved;
	}
	
	@Override
	public Symbol visitVariableExpression(VariableExpressionContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		if (!this.validator.mustBeDefined(scope, name, token)) {
			throw this.validator.getException();
		}
		
		Symbol variable = this.validator.getValidatedSymbol();
		
		if (ctx.index != null) {
			this.visit(ctx.index);
		}
		
		this.symbols.put(ctx, variable);
		
		return variable;
	}
	
	@Override
	public Symbol visitFunctionExpression(FunctionExpressionContext ctx) {
		Scope scope = this.getScope();
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
	public Symbol visitReturnStatement(ReturnStatementContext ctx) {
		if (ctx.expression() == null) {
			return null;
		}
		
		Scope scope = this.getScope();
		
		Symbol expr = this.visit(ctx.expression());
		Token token = ctx.expression().getStart();
		
		if (!this.validator.mustBeTypeCompatible((Symbol) scope, expr, token)) {
			throw this.validator.getException();
		}
		
		return expr;
	}
	
	@Override
	public Symbol visitFunctionDefinition(FunctionDefinitionContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		Symbol function = scope.resolve(name);
		
		this.scopes.push((Scope) function);
		
		this.visit(ctx.returnType());
		
		if (!ctx.returnType().getText().equals("nothing")) {
			if (!this.validator.mustReturn(function, ctx.statements())) {
				throw this.validator.getException();
			}
		}
		
		this.visit(ctx.statements());
		
		this.scopes.pop();
		
		return function;
	}
	
	@Override
	public Symbol visitLibraryRequirementsList(LibraryRequirementsListContext ctx) {
		LibrarySymbol library = (LibrarySymbol) this.getScope();
		LibrarySymbol requirement;
		
		for (ValidNameContext name : ctx.validName()) {
			if (!this.validator.mustBeDefined(library, name.getText(), name.getStart())) {
				throw this.validator.getException();
			}
			
			requirement = (LibrarySymbol) this.validator.getValidatedSymbol();
			library.defineRequirement(requirement);
		}
		
		return null;
	}
	
	@Override
	public Symbol visitLibraryInitializer(LibraryInitializerContext ctx) {
		if (ctx.validName() != null) {
			LibrarySymbol library = (LibrarySymbol) this.getScope();
			String name = ctx.validName().getText();
			Token token = ctx.validName().getStart();
			
			if (!this.validator.mustBeDefined(library, name, token)) {
				throw this.validator.getException();
			}
			
			library.setInitializer(this.validator.getValidatedSymbol());
		}
		
		return null;
	}
	
	@Override
	public Symbol visitLibraryDefinition(LibraryDefinitionContext ctx) {
		String name = ctx.validName().getText();
		Symbol library = this.getScope().resolve(name);
		
		this.scopes.push((Scope) library);
		super.visitLibraryDefinition(ctx);
		this.scopes.pop();
		
		return library;
	}
	
	@Override
	public Symbol visitThisExpression(ThisExpressionContext ctx) {
		if (!this.validator.mustBeDefined(this.getScope(), "this", ctx.getStart())) {
			throw this.validator.getException();
		}
		
		Symbol _this = this.validator.getValidatedSymbol();
		this.symbols.put(ctx, _this);
		
		return _this;
	}
	
	@Override
	public Symbol visitMethodDefinition(MethodDefinitionContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		Symbol method = scope.resolve(name);
		boolean hasReturn = false;
		
		this.scopes.push((Scope) method);
		
		this.visit(ctx.returnType());
		
		if (ctx.returnType().getText().equals("nothing")) {
			hasReturn = true;
		}
		
		for (StatementContext stat : ctx.statements().statement()) {
			this.visit(stat);
			
			if (stat.returnStatement() != null) {
				hasReturn = true;
			}
		}
		
		if (!hasReturn) {
			throw new MissReturnException(ctx.getStart(), method);
		}
		
		this.visit(ctx.statements());
		
		this.scopes.pop();
		
		return method;
	}
	
	@Override
	public Symbol visitParenthesis(ParenthesisContext ctx) {
		return this.visit(ctx.expression());
	}
	
	@Override
	public Symbol visitChainExpression(ChainExpressionContext ctx) {
		Scope scope = this.getScope();
		
		Symbol prevSymbol = null;
		Symbol symbol = null;
		int pushed = 0;
		
		for (ExpressionContext expr : ctx.expression()) {
			symbol = this.visit(expr);
			this.symbols.put(expr, symbol);
			
			if (this.validator.mustHaveAccess(scope, symbol, expr.getStart())) {
				if (symbol.getType() instanceof Scope) { // class
					this.scopes.push((Scope) symbol.getType()).toggleEnclosingScope();
					pushed++;
				} else if (symbol instanceof Scope) { // library
					this.scopes.push((Scope) symbol).toggleEnclosingScope();
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
	public Symbol visitValidImplementName(ValidImplementNameContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		if (!this.validator.mustBeDefined(scope, name, token)) {
			throw this.validator.getException();
		}
		
		Symbol _interface = this.validator.getValidatedSymbol();
		
		if (!this.validator.mustBeImplementableTypeValid(_interface, token)) {
			throw this.validator.getException();
		}
		
		return _interface;
	}
	
	@Override
	public Symbol visitExtendValidName(ExtendValidNameContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		if (!this.validator.mustBeDefined(scope, name, token)) {
			throw this.validator.getException();
		}
		
		if (!this.validator.mustBeExtendableValid(this.validator.getValidatedSymbol(), token)) {
			throw this.validator.getException();
		}
		
		ClassSymbol _extends = (ClassSymbol) this.validator.getValidatedSymbol();
		((ClassSymbol) scope).extendsFrom(_extends);
		
		return _extends;
	}
	
	@Override
	public Symbol visitStructDefinition(StructDefinitionContext ctx) {
		String name = ctx.validName().getText();
		ClassSymbol _class = (ClassSymbol) this.getScope().resolve(name);
		
		this.scopes.push(_class);
		super.visitStructDefinition(ctx);
		this.scopes.pop();
		
		return _class;
	}

}
