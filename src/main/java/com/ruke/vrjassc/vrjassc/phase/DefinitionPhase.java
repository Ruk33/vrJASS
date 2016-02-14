package com.ruke.vrjassc.vrjassc.phase;

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
import com.ruke.vrjassc.vrjassc.symbol.UserTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.VariableSymbol;
import com.ruke.vrjassc.vrjassc.util.TokenSymbolBag;
import com.ruke.vrjassc.vrjassc.util.Validator;

public class DefinitionPhase extends vrjassBaseVisitor<Symbol> {
	
	private TokenSymbolBag symbols;
	
	private Validator validator;
	
	private ScopeSymbol scope;
	
	private int classesCount;
	
	public DefinitionPhase(TokenSymbolBag symbols, ScopeSymbol scope) {
		this.symbols = symbols;
		this.validator = new Validator();
		this.scope = scope;
		this.validator.scope = scope;
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
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		Symbol type = new BuiltInTypeSymbol(name, this.scope, token);	
		
		this.symbols.put(ctx, type);
		this.scope.define(type);
		
		return type;
	}
	
	@Override
	public Symbol visitLibraryDefinition(LibraryDefinitionContext ctx) {
		String name = ctx.name.getText();
		Token token = ctx.name.getStart();

		LibrarySymbol library = new LibrarySymbol(name, null, token);
		
		this.defineOrThrowAlreadyDefinedException(this.scope, library);
		
		ScopeSymbol prev = this.scope;
		this.scope = library;
		
		super.visitLibraryDefinition(ctx);

		this.symbols.put(ctx, library);
		this.scope = prev;
		
		return library;
	}
	
	@Override
	public Symbol visitInterfaceDefinition(InterfaceDefinitionContext ctx) {
		String name = ctx.validName().getText();
		Token token = ctx.getStart();
		
		InterfaceSymbol _interface = new InterfaceSymbol(name, 0, null, token);
		_interface.setModifier(Modifier.PUBLIC, ctx.PUBLIC() != null);
		
		this.defineOrThrowAlreadyDefinedException(this.scope, _interface);
		
		ScopeSymbol prev = this.scope;
		this.scope = _interface;
		
		super.visitInterfaceDefinition(ctx);
		
		this.symbols.put(ctx, _interface);
		this.scope = prev;
		
		return _interface;
	}
	
	@Override
	public Symbol visitStructDefinition(StructDefinitionContext ctx) {		
		String name = ctx.name.getText();
		Token token = ctx.name.getStart();
		
		ClassSymbol _class = new ClassSymbol(name, ++this.classesCount, null, token);
		_class.setModifier(Modifier.PUBLIC, ctx.PUBLIC() != null);
		
		this.defineOrThrowAlreadyDefinedException(this.scope, _class);
		
		ScopeSymbol prev = this.scope;
		this.scope = _class;
		
		super.visitStructDefinition(ctx);
		
		this.symbols.put(ctx, _class);
		this.scope = prev;
		
		return _class;
	}
	
	@Override
	public Symbol visitParameter(ParameterContext ctx) {
		Symbol parameter = this.visit(ctx.variableDeclaration());
		parameter.setModifier(Modifier.LOCAL, true);
		
		((FunctionSymbol) this.scope).defineParam(parameter);
		
		return parameter;
	}
	
	@Override
	public Symbol visitReturnType(ReturnTypeContext ctx) {
		Symbol type = null;
		
		if (ctx.expression() != null) {
			type = this.scope.resolve(ctx.expression().getText());
			if (type instanceof Type) {
				this.scope.setType((Type) type);
			}
		}
		
		return type;
	}
	
	@Override
	public Symbol visitFunctionSignature(FunctionSignatureContext ctx) {		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		FunctionSymbol function = new FunctionSymbol(name, null, token);
		
		function.setModifier(Modifier.PUBLIC, ctx.PUBLIC() != null);
		
		if (this.scope instanceof UserTypeSymbol) {
			function.setModifier(Modifier.STATIC, ctx.STATIC() != null);
		} else {
			function.setModifier(Modifier.STATIC, true);
		}
		
		this.defineOrThrowAlreadyDefinedException(this.scope, function);
		
		ScopeSymbol prev = this.scope;
		this.scope = function;
		
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
		this.scope = prev;
		
		return function;
	}
	
	@Override
	public Symbol visitFunctionDefinition(FunctionDefinitionContext ctx) {
		FunctionSymbol function = (FunctionSymbol) this.visit(ctx.functionSignature());
		
		this.symbols.put(ctx, function);
		
		ScopeSymbol prev = this.scope;
		this.scope = function;
		
		for (StatementContext stat : ctx.statement()) {
			this.visit(stat);
		}
		
		this.scope = prev;
		
		return function;
	}
	
	@Override
	public Symbol visitVariableDeclaration(VariableDeclarationContext ctx) {
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		Symbol variable = new VariableSymbol(name, this.scope, token);
		variable.setModifier(Modifier.ARRAY, ctx.ARRAY() != null);
		
		Symbol type = this.scope.resolve(ctx.type.getText());
		
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
		variable.setModifier(Modifier.STATIC, true);
		
		this.symbols.put(ctx, variable);
		this.defineOrThrowAlreadyDefinedException(this.scope, variable);
		
		return variable;
	}
	
	@Override
	public Symbol visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		Symbol variable = this.visit(ctx.variableDeclaration());
		
		variable.setModifier(Modifier.LOCAL, true);
		variable.setModifier(Modifier.PRIVATE, true);
		
		this.symbols.put(ctx, variable);
		this.defineOrThrowAlreadyDefinedException(this.scope, variable);
		
		return variable;
	}
	
	@Override
	public Symbol visitPropertyStatement(PropertyStatementContext ctx) {
		Symbol property = this.visit(ctx.variableDeclaration());
		
		property.setModifier(Modifier.PUBLIC, ctx.PUBLIC() != null);
		property.setModifier(Modifier.STATIC, ctx.STATIC() != null);
		
		this.symbols.put(ctx, property);
		this.defineOrThrowAlreadyDefinedException(this.scope, property);
		
		return property;
	}

}
