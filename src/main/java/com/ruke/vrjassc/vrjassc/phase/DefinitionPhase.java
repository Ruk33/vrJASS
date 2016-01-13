package com.ruke.vrjassc.vrjassc.phase;

import java.util.Stack;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionSignatureContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InterfaceDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LocalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NativeDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParameterContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PropertyStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ReturnTypeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StructDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.TypeDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.VariableDeclarationContext;
import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.InterfaceSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LibrarySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.Scope;
import com.ruke.vrjassc.vrjassc.symbol.ScopeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;
import com.ruke.vrjassc.vrjassc.symbol.VariableSymbol;
import com.ruke.vrjassc.vrjassc.util.TokenSymbolBag;
import com.ruke.vrjassc.vrjassc.util.Validator;

public class DefinitionPhase extends vrjassBaseVisitor<Symbol> {
	
	private TokenSymbolBag symbols;
	
	private Validator validator;
	
	private ScopeSymbol scope;
	
	private Stack<ScopeSymbol> scopes;
		
	public DefinitionPhase(TokenSymbolBag symbols, ScopeSymbol scope) {
		this.symbols = symbols;
		this.validator = new Validator();
		this.scope = scope;
		this.scopes = new Stack<ScopeSymbol>();
		
		this.scopes.push(this.scope);
	}
		
	private void defineOrThrowAlreadyDefinedException(Scope scope, Symbol child) {
		if (!this.validator.mustNotBeDefined(scope, child.getName(), child.getToken())) {
			if (!(scope instanceof LibrarySymbol && child instanceof ClassSymbol)) { 
				throw this.validator.getException();
			}
		}
		
		scope.define(child);
	}
	
	@Override
	public Symbol visitNativeDefinition(NativeDefinitionContext ctx) {
		return this.visit(ctx.functionSignature());
	}
	
	@Override
	public Symbol visitTypeDefinition(TypeDefinitionContext ctx) {
		Scope scope = this.scopes.peek();
		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		Symbol type = new BuiltInTypeSymbol(name, scope, token);	
		
		this.symbols.put(ctx, type);
		scope.define(type);
		
		return type;
	}
	
	@Override
	public Symbol visitLibraryDefinition(LibraryDefinitionContext ctx) {
		String name = ctx.name.getText();
		Token token = ctx.name.getStart();

		LibrarySymbol library = new LibrarySymbol(name, null, token);
		
		this.defineOrThrowAlreadyDefinedException(this.scopes.peek(), library);
		this.scopes.push(library);
		
		super.visitLibraryDefinition(ctx);

		this.symbols.put(ctx, library);
		this.scopes.pop();
		
		return library;
	}
	
	@Override
	public Symbol visitInterfaceDefinition(InterfaceDefinitionContext ctx) {
		String name = ctx.validName().getText();
		Token token = ctx.getStart();
		
		InterfaceSymbol _interface = new InterfaceSymbol(name, null, token);
		_interface.setModifier(Modifier.PUBLIC, ctx.PUBLIC() != null);
		
		this.defineOrThrowAlreadyDefinedException(this.scopes.peek(), _interface);
		this.scopes.push(_interface);
		
		super.visitInterfaceDefinition(ctx);
		
		this.symbols.put(ctx, _interface);
		this.scopes.pop();
		
		return _interface;
	}
		
	@Override
	public Symbol visitStructDefinition(StructDefinitionContext ctx) {
		Scope scope = this.scopes.peek();
		
		String name = ctx.name.getText();
		Token token = ctx.name.getStart();
		
		ClassSymbol _class = new ClassSymbol(name, null, token);
		_class.setModifier(Modifier.PUBLIC, ctx.PUBLIC() != null);
		
		this.defineOrThrowAlreadyDefinedException(scope, _class);
		this.scopes.push(_class);
		
		super.visitStructDefinition(ctx);
		
		this.symbols.put(ctx, _class);
		this.scopes.pop();
		
		return _class;
	}
	
	@Override
	public Symbol visitParameter(ParameterContext ctx) {
		Symbol parameter = this.visit(ctx.variableDeclaration());
		parameter.setModifier(Modifier.LOCAL, true);
		
		((FunctionSymbol) this.scopes.peek()).defineParam(parameter);
		
		return parameter;
	}
	
	@Override
	public Symbol visitReturnType(ReturnTypeContext ctx) {
		Symbol type = null;
		
		if (ctx.expression() != null) {
			type = this.scopes.peek().resolve(ctx.expression().getText());
			if (type instanceof Type) {
				this.scopes.peek().setType((Type) type);
			}
		}
		
		return type;
	}
	
	@Override
	public Symbol visitFunctionSignature(FunctionSignatureContext ctx) {
		Scope scope = this.scopes.peek();
		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		FunctionSymbol function = new FunctionSymbol(name, null, token);
		
		function.setModifier(Modifier.PUBLIC, ctx.PUBLIC() != null);
		
		if (scope instanceof ClassSymbol) {
			function.setModifier(Modifier.STATIC, ctx.STATIC() != null);
		} else {
			function.setModifier(Modifier.STATIC, true);
		}
		
		this.defineOrThrowAlreadyDefinedException(scope, function);
		this.scopes.push(function);
		
		if (ctx.parameters() != null) {
			this.visit(ctx.parameters());
		}
		
		if (function.getParentScope() instanceof ClassSymbol) {
			if (!function.hasModifier(Modifier.STATIC)) {
				Symbol _this = new Symbol("this", function, null);
				_this.setType((Type) function.getParentScope());
				
				function.define(_this);
			}
		}
		
		if (ctx.returnType() != null) {
			this.visit(ctx.returnType());
		}
		
		this.symbols.put(ctx, function);
		this.scopes.pop();
		
		return function;
	}
	
	@Override
	public Symbol visitFunctionDefinition(FunctionDefinitionContext ctx) {
		FunctionSymbol function = (FunctionSymbol) this.visit(ctx.functionSignature());
		
		this.symbols.put(ctx, function);
		this.scopes.push(function);
		
		for (StatementContext stat : ctx.statement()) {
			this.visit(stat);
		}
		
		this.scopes.pop();
		
		return function;
	}
	
	@Override
	public Symbol visitVariableDeclaration(VariableDeclarationContext ctx) {
		Scope scope = this.scopes.peek();
		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		Symbol variable = new VariableSymbol(name, scope, token);
		variable.setModifier(Modifier.ARRAY, ctx.ARRAY() != null);
		
		Symbol type = scope.resolve(ctx.type.getText());
		
		if (type instanceof Type) {
			variable.setType((Type) type);
		}
		
		this.symbols.put(ctx, variable);
		
		return variable;
	}
	
	@Override
	public Symbol visitGlobalVariableStatement(GlobalVariableStatementContext ctx) {
		Symbol variable = this.visit(ctx.variableDeclaration());
		variable.setModifier(Modifier.PUBLIC, ctx.PUBLIC() != null);
		
		this.symbols.put(ctx, variable);
		this.defineOrThrowAlreadyDefinedException(this.scopes.peek(), variable);
		
		return variable;
	}
	
	@Override
	public Symbol visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		Symbol variable = this.visit(ctx.variableDeclaration());
		
		variable.setModifier(Modifier.LOCAL, true);
		variable.setModifier(Modifier.PRIVATE, true);
		
		this.symbols.put(ctx, variable);
		this.defineOrThrowAlreadyDefinedException(this.scopes.peek(), variable);
		
		return variable;
	}
	
	@Override
	public Symbol visitPropertyStatement(PropertyStatementContext ctx) {
		Symbol property = this.visit(ctx.variableDeclaration());
		
		property.setModifier(Modifier.PUBLIC, ctx.PUBLIC() != null);
		property.setModifier(Modifier.STATIC, ctx.STATIC() != null);
		
		this.symbols.put(ctx, property);
		this.defineOrThrowAlreadyDefinedException(this.scopes.peek(), property);
		
		return property;
	}

}
