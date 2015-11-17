package com.ruke.vrjassc.vrjassc.phase;

import java.util.Stack;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InterfaceDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InterfaceStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LocalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NativeDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParameterContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PropertyStatementContext;
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
			if (this.validator.getValidatedSymbol() instanceof LibrarySymbol == false) { 
				throw this.validator.getException();
			}
		}
		
		scope.define(child);
	}
	
	@Override
	public Symbol visitTypeDefinition(TypeDefinitionContext ctx) {
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		Symbol type = new BuiltInTypeSymbol(name, null, token);	
		
		this.symbols.put(ctx, type);
		this.scopes.peek().define(type);
		
		return type;
	}
	
	@Override
	public Symbol visitNativeDefinition(NativeDefinitionContext ctx) {		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		FunctionSymbol _native = new FunctionSymbol(name, null, token);
		
		if (ctx.returnType().validType() != null) {
			String type = ctx.returnType().validType().getText();
			_native.setType((Type) this.scopes.peek().resolve(type));
		}
		
		this.defineOrThrowAlreadyDefinedException(this.scopes.peek(), _native);
		this.scopes.push(_native);
		
		if (ctx.parameters() != null) {
			for (ParameterContext parameter : ctx.parameters().parameter()) {
				_native.defineParam(this.visit(parameter));
			}
		}
		
		this.symbols.put(ctx, _native);
		this.scopes.pop();
		
		return _native;
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
	public Symbol visitInterfaceStatement(InterfaceStatementContext ctx) {
		String name = ctx.validName().getText();
		Token token = ctx.getStart();
		
		FunctionSymbol function = new FunctionSymbol(name, null, token);
		
		this.defineOrThrowAlreadyDefinedException(this.scopes.peek(), function);
		this.scopes.push(function);
		
		if (ctx.parameters().parameter() != null) {
			for (ParameterContext param : ctx.parameters().parameter()) {
				function.defineParam(this.visit(param));
			}
		}
		
		this.symbols.put(ctx, function);
		this.scopes.peek();
		
		return function;
	}
	
	@Override
	public Symbol visitStructDefinition(StructDefinitionContext ctx) {
		String name = ctx.name.getText();
		Token token = ctx.name.getStart();
		
		ClassSymbol _class = new ClassSymbol(name, null, token);
		_class.setModifier(Modifier.PUBLIC, ctx.PUBLIC() != null);
		
		this.defineOrThrowAlreadyDefinedException(this.scopes.peek(), _class);
		this.scopes.push(_class);
		
		super.visitStructDefinition(ctx);
		
		this.symbols.put(ctx, _class);
		this.scopes.pop();
		
		return _class;
	}
	
	@Override
	public Symbol visitFunctionDefinition(FunctionDefinitionContext ctx) {		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		FunctionSymbol function = new FunctionSymbol(name, null, token);
		
		function.setModifier(Modifier.PUBLIC, ctx.PUBLIC() != null);
		function.setModifier(Modifier.STATIC, ctx.STATIC() != null);
		
		this.defineOrThrowAlreadyDefinedException(this.scopes.peek(), function);
		this.scopes.push(function);
		
		if (ctx.parameters() != null) {			
			for (ParameterContext param : ctx.parameters().parameter()) {
				function.defineParam(this.visit(param));
			}
		}
		
		if (function.getParentScope() instanceof ClassSymbol) {
			if (!function.hasModifier(Modifier.STATIC)) {
				Symbol _this = new Symbol("this", function, null);
				
				_this.setType((Type) function.getParentScope());
				function.define(_this);
			}
		}
		
		super.visitFunctionDefinition(ctx);
		
		this.symbols.put(ctx, function);
		this.scopes.pop();
		
		return function;
	}
	
	@Override
	public Symbol visitVariableDeclaration(VariableDeclarationContext ctx) {
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		Symbol variable = new VariableSymbol(name, null, token);
		
		variable.setModifier(Modifier.ARRAY, ctx.ARRAY() != null);
		variable.setType((Type) this.scope.resolve(ctx.validType().getText()));
		
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
