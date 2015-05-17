package com.ruke.vrjassc.vrjassc.visitor;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ClassDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ClassStatementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InterfaceDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InterfaceMethodDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InterfaceStatementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryStatementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LocalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MethodDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ModuleDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NativeDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParameterContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PropertyStatementContext;
import com.ruke.vrjassc.vrjassc.exception.AlreadyDefinedFunctionException;
import com.ruke.vrjassc.vrjassc.exception.AlreadyDefinedVariableException;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.InterfaceSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LibrarySymbol;
import com.ruke.vrjassc.vrjassc.symbol.MethodSymbol;
import com.ruke.vrjassc.vrjassc.symbol.ModuleSymbol;
import com.ruke.vrjassc.vrjassc.symbol.NativeFunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.ParameterSymbol;
import com.ruke.vrjassc.vrjassc.symbol.PrimitiveType;
import com.ruke.vrjassc.vrjassc.symbol.PropertySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.VariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Visibility;

public class SymbolVisitor extends vrjassBaseVisitor<Void> {
	
	protected Symbol globalScope;

	protected Symbol scope;

	public SymbolVisitor() {
		this.globalScope = new Symbol(null, null, null, null, null, null);
		this.scope = this.globalScope;
	}

	public Symbol getGlobalScope() {
		return this.globalScope;
	}
	
	public Visibility getVisibility(Token visibility) {
		if (visibility == null) {
			return null;
		}

		if (visibility.getText().equals("private")) {
			return Visibility.PRIVATE;
		}

		return Visibility.PUBLIC;
	}
	
	@Override
	public Void visitModuleDefinition(ModuleDefinitionContext ctx) {
		String name = ctx.moduleName.getText();
		Visibility visibility = this.getVisibility(null);
		
		this.scope = new ModuleSymbol(name, visibility, this.scope, ctx.moduleName);
		
		for (ClassStatementsContext statement : ctx.classStatements()) {
			this.visit(statement);
		}
		
		this.scope = this.scope.getParent();
		
		return null;
	}
	
	@Override
	public Void visitInterfaceMethodDefinition(
			InterfaceMethodDefinitionContext ctx) {
		String name = ctx.methodName.getText();
		String type = ctx.returnType().getText();
		boolean isStatic = ctx.STATIC() != null;
		Visibility visibility = this.getVisibility(ctx.visibility);
		
		this.scope = new MethodSymbol(
			name,
			type,
			isStatic,
			true,
			visibility,
			this.scope,
			ctx.methodName
		);
		
		this.visit(ctx.parameters());
		
		this.scope = this.scope.getParent();
		
		return null;
	}

	@Override
	public Void visitInterfaceDefinition(InterfaceDefinitionContext ctx) {
		String name = ctx.interfaceName.getText();
		Visibility visibility = this.getVisibility(ctx.visibility);
		
		this.scope = new InterfaceSymbol(name, visibility, this.scope, ctx.interfaceName);
		
		for (InterfaceStatementsContext statement : ctx.interfaceStatements()) {
			this.visit(statement);
		}
		
		this.scope = this.scope.getParent();
		
		
		return null;
	}
	
	@Override
	public Void visitGlobalVariableStatement(GlobalVariableStatementContext ctx) {
		String name = ctx.varName.getText();
		String type = ctx.variableType().getText();
		boolean isConstant = ctx.CONSTANT() != null;
		boolean isArray = ctx.array != null;
		Visibility visibility = this.getVisibility(ctx.visibility);

		new VariableSymbol(name, type, isConstant, isArray, true, visibility,
				this.scope, ctx.varName);

		return null;
	}

	@Override
	public Void visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		String name = ctx.varName.getText();
		String type = ctx.variableType().getText();
		boolean isArray = ctx.array != null;
		Symbol variable = this.scope.resolve(name, PrimitiveType.VARIABLE,
				false);

		if (variable != null) {
			throw new AlreadyDefinedVariableException(ctx.varName,
					(VariableSymbol) variable);
		}

		new VariableSymbol(name, type, false, isArray, false,
				Visibility.LOCAL, this.scope, ctx.varName);

		return null;
	}

	@Override
	public Void visitParameter(ParameterContext ctx) {
		String name = ctx.variableName().getText();
		String type = ctx.variableType().getText();
		Symbol variable = this.scope.resolve(name, PrimitiveType.VARIABLE,
				false);

		if (variable != null) {
			throw new AlreadyDefinedVariableException(ctx.variableName().getStart(),
					(VariableSymbol) variable);
		}

		new ParameterSymbol(name, type, this.scope, ctx.variableName().getStart());

		return null;
	}

	@Override
	public Void visitPropertyStatement(PropertyStatementContext ctx) {
		String name = ctx.propertyName.getText();
		String type = ctx.variableType().getText();
		boolean isStatic = ctx.STATIC() != null;
		boolean isArray = true;
		Visibility visibility = this.getVisibility(ctx.visibility);

		if (isStatic) {
			isArray = ctx.array != null;
		}

		new PropertySymbol(
			name, type, isStatic, isArray, visibility, this.scope, ctx.propertyName
		);

		return null;
	}

	@Override
	public Void visitMethodDefinition(MethodDefinitionContext ctx) {
		String name = ctx.methodName.getText();
		String type = ctx.returnType().getText();
		boolean isStatic = ctx.STATIC() != null;
		Visibility visibility = this.getVisibility(ctx.visibility);

		this.scope = new MethodSymbol(name, type, isStatic, false, visibility,
				this.scope, ctx.methodName);

		this.visit(ctx.parameters());
		this.visit(ctx.statements());

		this.scope = this.scope.getParent();

		return null;
	}

	@Override
	public Void visitNativeDefinition(NativeDefinitionContext ctx) {
		String name = ctx.functionName.getText();
		String type = "nothing";

		if (ctx.returnType() != null && ctx.returnType().variableType() != null) {
			type = ctx.returnType().variableType().getText();
		}

		this.scope = new NativeFunctionSymbol(name, type, this.scope,
				ctx.functionName);

		this.visit(ctx.parameters());

		this.scope = this.scope.getParent();

		return null;
	}

	@Override
	public Void visitFunctionDefinition(FunctionDefinitionContext ctx) {
		String name = ctx.functionName.getText();
		String type = ctx.returnType().getText();
		Visibility visibility = this.getVisibility(ctx.visibility);
		Symbol function = this.scope.resolve(name, PrimitiveType.FUNCTION,
				false);

		if (function != null) {
			throw new AlreadyDefinedFunctionException(ctx.functionName,
					(FunctionSymbol) function);
		}

		this.scope = new FunctionSymbol(name, type, visibility, this.scope,
				ctx.functionName);

		this.visit(ctx.parameters());
		this.visit(ctx.statements());

		this.scope = this.scope.getParent();

		return null;
	}

	@Override
	public Void visitClassDefinition(ClassDefinitionContext ctx) {
		String name = ctx.className.getText();
		boolean isAbstract = ctx.ABSTRACT() != null;
		
		this.scope = new ClassSymbol(
			name,
			isAbstract,
			Visibility.PUBLIC,
			this.scope,
			ctx.className
		);

		for (ClassStatementsContext statement : ctx.classStatements()) {
			this.visit(statement);
		}

		this.scope = this.scope.getParent();

		return null;
	}

	@Override
	public Void visitLibraryDefinition(LibraryDefinitionContext ctx) {
		String name = ctx.libraryName.getText();

		this.scope = new LibrarySymbol(name, this.scope, ctx.libraryName);

		for (LibraryStatementsContext statements : ctx.libraryStatements()) {
			this.visit(statements);
		}

		this.scope = this.scope.getParent();

		return null;
	}

}
