package visitor;

import java.util.HashMap;

import exception.AlreadyDefinedVariableException;
import symbol.VariableSymbol;
import symbol.Visibility;
import antlr4.vrjassBaseVisitor;
import antlr4.vrjassParser.ClassDefinitionContext;
import antlr4.vrjassParser.ClassStatementsContext;
import antlr4.vrjassParser.FunctionDefinitionContext;
import antlr4.vrjassParser.GlobalVariableStatementContext;
import antlr4.vrjassParser.LibraryDefinitionContext;
import antlr4.vrjassParser.LibraryStatementsContext;
import antlr4.vrjassParser.LocalVariableStatementContext;
import antlr4.vrjassParser.ParameterContext;
import antlr4.vrjassParser.PropertyStatementContext;

public class VariableFinder extends vrjassBaseVisitor<Void> {

	protected HashMap<String, VariableSymbol> globals;
	protected HashMap<String, VariableSymbol> locals;
	
	protected MainVisitor main;
	
	protected String scopeName;
	
	protected String funcName;
	
	protected String className;
	
	public VariableFinder(MainVisitor main) {
		this.globals = new HashMap<String, VariableSymbol>();
		this.locals = new HashMap<String, VariableSymbol>();
		this.main = main;
	}

	public VariableSymbol getGlobal(String name) {
		return this.globals.get(name);
	}

	public VariableSymbol getLocal(String name) {
		return this.locals.get(name);
	}
	
	protected VariableSymbol put(VariableSymbol variable) {
		VariableSymbol alreadyDefined = null;
		
		if (variable.isGlobal()) {
			alreadyDefined = this.globals.get(variable.getName());
		} else {
			alreadyDefined = this.locals.get(variable.getName());
		}
		
		if (alreadyDefined != null) {
			throw new AlreadyDefinedVariableException(
				variable.getToken(), alreadyDefined
			);
		}
		
		if (variable.isGlobal()) {
			this.globals.put(variable.getName(), variable);
		} else {
			this.locals.put(variable.getName(), variable);
		}
		
		return variable;
	}
	
	@Override
	public Void visitGlobalVariableStatement(GlobalVariableStatementContext ctx) {
		String prefix = this.main.getPrefixer().getForScope(ctx.visibility, this.scopeName);
		String name = prefix + ctx.varName.getText();
		String type = ctx.variableType().getText();
		boolean isArray = ctx.array != null;
		Visibility visibility = this.main.getVisibility(ctx.visibility);
		
		VariableSymbol variable = new VariableSymbol(
			prefix + name,
			type,
			true,
			isArray,
			null,
			ctx.varName,
			visibility,
			this.scopeName
		);
		
		this.put(variable);
		
		return null;
	}
	
	@Override
	public Void visitPropertyStatement(PropertyStatementContext ctx) {
		String prefix = this.main.getPrefixer().getForClass(ctx.visibility, this.scopeName, this.className);
		String name = ctx.propertyName.getText();
		String type = ctx.variableType().getText();
		Visibility visibility = this.main.getVisibility(ctx.visibility);
		VariableSymbol property = new VariableSymbol(
			prefix + name,
			type,
			true,
			true,
			null,
			ctx.propertyName,
			visibility,
			this.className
		);
		
		this.put(property);
		
		return null;
	}
	
	@Override
	public Void visitClassDefinition(ClassDefinitionContext ctx) {
		String prevClassName = this.className;
		
		this.className = ctx.className.getText();
		
		for (ClassStatementsContext stat : ctx.classStatements()) {
			this.visit(stat);
		}
		
		this.className = prevClassName;
		
		return null;
	}
	
	@Override
	public Void visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		String prefix = this.main.getPrefixer().getForScope(true, this.funcName);
		String name = ctx.varName.getText();
		String type = ctx.variableType().getText();
		boolean isArray = ctx.array != null;
		
		VariableSymbol variable = new VariableSymbol(
			prefix + name,
			type,
			false,
			isArray,
			null,
			ctx.varName,
			Visibility.PRIVATE,
			this.funcName
		);
		
		this.put(variable);
		
		return null;
	}
	
	@Override
	public Void visitParameter(ParameterContext ctx) {
		String prefix = this.main.getPrefixer().getForScope(true, this.funcName);
		String name = ctx.ID().getText();
		String type = ctx.variableType().getText();
		VariableSymbol variable = new VariableSymbol(
			prefix + name,
			type,
			false,
			false,
			null,
			ctx.ID().getSymbol(),
			Visibility.PRIVATE,
			this.funcName
		);
		
		this.put(variable);
		
		return null;
	}
	
	@Override
	public Void visitFunctionDefinition(FunctionDefinitionContext ctx) {
		String prevFuncName = this.funcName;
		
		this.funcName = ctx.functionName.getText();
		
		this.visit(ctx.parameters());
		this.visit(ctx.statements());
		
		this.funcName = prevFuncName;
		
		return null;
	}
	
	@Override
	public Void visitLibraryDefinition(LibraryDefinitionContext ctx) {
		String prevScopeName = this.scopeName;
		
		this.scopeName = ctx.libraryName.getText();
		
		for (LibraryStatementsContext library : ctx.libraryStatements()) {
			this.visit(library);
		}
		
		this.scopeName = prevScopeName;
		
		return null;
	}
	
}
