package com.ruke.vrjassc.vrjassc.phase;

import java.util.Stack;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LocalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MethodDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParameterContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PropertyStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StructDefinitionContext;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.GlobalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LibrarySymbol;
import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.MethodSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.PropertySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Scope;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;
import com.ruke.vrjassc.vrjassc.util.TokenSymbolBag;
import com.ruke.vrjassc.vrjassc.util.Validator;

public class DefinitionPhase extends vrjassBaseVisitor<Void> {
	
	private TokenSymbolBag symbols;
	
	private Validator validator;
	
	private Stack<Scope> scopes;
	
	public DefinitionPhase(TokenSymbolBag symbols, Scope scope) {
		this.symbols = symbols;
		this.validator = new Validator();
		this.scopes = new Stack<Scope>();
		
		this.scopes.push(scope);
	}
	
	public Scope getScope() {
		return this.scopes.peek();
	}
	
	@Override
	public Void visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		String type = ctx.validType().getText();
		Token token = ctx.validName().getStart();
		
		if (!this.validator.mustNotBeDefined(scope, name, token)) {
			throw this.validator.getException();
		}
				
		Symbol variable = new LocalVariableSymbol(name, scope, token);
		
		//variable.setModifier(Modifier.ARRAY, ctx.ARRAY() != null);
		variable.setType((Type) scope.resolve(type));
		
		scope.define(variable);
		this.symbols.saveVariable(ctx, variable);
		
		if (ctx.value != null) {
			this.visit(ctx.value);
		}
		
		return null;
	}
	
	@Override
	public Void visitParameter(ParameterContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		String type = ctx.validType().getText();
		Token token = ctx.validName().getStart();
		
		Symbol variable = new LocalVariableSymbol(name, scope, token);
		
		variable.setType((Type) scope.resolve(type));
		
		((FunctionSymbol) scope).defineParam(variable);
		this.symbols.saveParameter(ctx, variable);
		
		return null;
	}
		
	@Override
	public Void visitFunctionDefinition(FunctionDefinitionContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		String type = ctx.returnType().getText();
		Token token = ctx.validName().getStart();
		
		if (!this.validator.mustNotBeDefined(scope, name, token)) {
			throw this.validator.getException();
		}
				
		FunctionSymbol function = new FunctionSymbol(name, scope, token);
		
		if (ctx.visibility().PUBLIC() != null) {
			function.setModifier(Modifier.PUBLIC, true);
		} else {
			function.setModifier(Modifier.PRIVATE, true);
		}
		
		scope.define(function);
		this.symbols.saveFunction(ctx, function);
		
		this.scopes.push(function);
		super.visitFunctionDefinition(ctx);
		this.scopes.pop();
		
		return null;
	}
	
	@Override
	public Void visitLibraryDefinition(LibraryDefinitionContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		if (!this.validator.mustNotBeDefined(scope, name, token)) {
			throw this.validator.getException();
		}
				
		LibrarySymbol library = new LibrarySymbol(name, scope, token);
		
		scope.define(library);
		this.symbols.saveLibrary(ctx, library);
		
		this.scopes.push(library);
		super.visitLibraryDefinition(ctx);
		this.scopes.pop();
		
		return null;
	}
	
	@Override
	public Void visitGlobalVariableStatement(GlobalVariableStatementContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		String type = ctx.validType().getText();
		Token token = ctx.validName().getStart();
		
		if (!this.validator.mustNotBeDefined(scope, name, token)) {
			throw this.validator.getException();
		}
				
		Symbol variable = new GlobalVariableSymbol(name, scope, token);
		
		if (ctx.publicPrivate() != null) {
			if (ctx.publicPrivate().PUBLIC() != null) {
				variable.setModifier(Modifier.PUBLIC, true);
			} else {
				variable.setModifier(Modifier.PRIVATE, true);
			}
		}
		
		//variable.setModifier(Modifier.ARRAY, ctx.ARRAY() != null);
		variable.setType((Type) scope.resolve(type));
		
		scope.define(variable);
		this.symbols.saveGlobalVariable(ctx, variable);
		
		if (ctx.value != null) {
			this.visit(ctx.value);
		}
		
		return null;
	}
	
	@Override
	public Void visitStructDefinition(StructDefinitionContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.name.getText();
		Token token = ctx.name.getStart();
		
		if (!this.validator.mustNotBeDefined(scope, name, token)) {
			// check the resolved symbol is a type since
			// libraries can share name with structs
			if (this.validator.getValidatedSymbol() instanceof Type) {
				throw this.validator.getException();
			}
		}
		
		ClassSymbol _class = new ClassSymbol(name, scope, token);
		_class.setModifier(Modifier.PUBLIC, true);
		
		scope.define(_class);
		this.symbols.saveClass(ctx, _class);
		
		this.scopes.push(_class);
		super.visitStructDefinition(ctx);
		this.scopes.pop();
		
		return null;
	}
	
	@Override
	public Void visitMethodDefinition(MethodDefinitionContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		String type = ctx.returnType().getText();
		Token token = ctx.validName().getStart();
		
		if (!this.validator.mustNotBeDefined(scope, name, token)) {
			throw this.validator.getException();
		}
		
		MethodSymbol method = new MethodSymbol(name, scope, token);
		
		if (ctx.visibility().PUBLIC() != null) {
			method.setModifier(Modifier.PUBLIC, true);
		} else {
			method.setModifier(Modifier.PRIVATE, true);
		}
		
		method.setModifier(Modifier.STATIC, ctx.STATIC() != null);
		method.setType((Type) scope.resolve(type));
		
		scope.define(method);
		this.symbols.saveMethod(ctx, method);
		
		this.scopes.push(method);
		super.visitMethodDefinition(ctx);
		this.scopes.pop();
		
		return null;
	}
	
	@Override
	public Void visitPropertyStatement(PropertyStatementContext ctx) {
		Scope scope = this.getScope();
		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		if (!this.validator.mustNotBeDefined(scope, name, token)) {
			throw this.validator.getException();
		}
		
		Symbol property = new PropertySymbol(name, scope, token);
		
		if (ctx.visibility().PUBLIC() != null) {
			property.setModifier(Modifier.PUBLIC, true);
		} else {
			property.setModifier(Modifier.PRIVATE, true);
		}
		
		property.setModifier(Modifier.ARRAY, ctx.ARRAY() != null);
		property.setModifier(Modifier.STATIC, ctx.STATIC() != null);
		
		scope.define(property);
		this.symbols.saveProperty(ctx, property);
		
		return null;
	}

}
