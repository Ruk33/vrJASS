package visitor;

import java.util.HashMap;

import org.antlr.v4.runtime.Token;

import exception.AlreadyDefinedFunctionException;
import symbol.FunctionSymbol;
import symbol.MethodSymbol;
import symbol.VariableSymbol;
import symbol.Visibility;
import antlr4.vrjassBaseVisitor;
import antlr4.vrjassParser.ClassDefinitionContext;
import antlr4.vrjassParser.ClassStatementsContext;
import antlr4.vrjassParser.FunctionDefinitionContext;
import antlr4.vrjassParser.LibraryDefinitionContext;
import antlr4.vrjassParser.LibraryStatementsContext;
import antlr4.vrjassParser.MethodDefinitionContext;
import antlr4.vrjassParser.ParameterContext;
import antlr4.vrjassParser.ParametersContext;

public class FunctionFinder extends vrjassBaseVisitor<Void> {

	protected MainVisitor main;
	protected String scopeName;
	protected String className;
	protected HashMap<String, FunctionSymbol> functions;
	protected FunctionSymbol lastFunction;
	
	public FunctionFinder(MainVisitor main) {
		this.main = main;
		this.functions = new HashMap<String, FunctionSymbol>();
		this.lastFunction = null;
	}
	
	public FunctionSymbol get(String name) {
		return this.functions.get(name);
	}
	
	@Override
	public Void visitParameters(ParametersContext ctx) {
		VariableSymbol variable;
		
		for (ParameterContext param : ctx.parameter()) {
			variable = new VariableSymbol(
				param.ID().getText(),
				param.variableType().getText(),
				false,
				false,
				null,
				param.ID().getSymbol(),
				Visibility.PRIVATE,
				this.lastFunction.getName()
			);
			
			this.lastFunction.addParam(variable);
		}
		
		return null;
	}
	
	protected FunctionSymbol defineFunction(
			Token funcName,
			String prefix,
			String name,
			String returnType,
			Visibility visibility,
			boolean method
			) {
		FunctionSymbol func = this.functions.get(prefix + name);
		
		if (func != null) {
			throw new AlreadyDefinedFunctionException(funcName, func);
		}
		
		if (method) {
			func = new MethodSymbol(
				this.scopeName, prefix + name, returnType, visibility, funcName
			);
		} else {
			func = new FunctionSymbol(
				this.scopeName, prefix + name, returnType, visibility, funcName
			);
		}
		
		return func;
	}
	
	@Override
	public Void visitFunctionDefinition(FunctionDefinitionContext ctx) {
		String prefix = this.main.getPrefixer().getForScope(ctx.visibility, this.scopeName);
		String name = ctx.functionName.getText();
		String returnType = this.main.visit(ctx.returnType());
		Visibility visibility = this.main.getVisibility(ctx.visibility);
		
		this.lastFunction = this.defineFunction(
			ctx.functionName, prefix, name, returnType, visibility, false
		);
		
		this.functions.put(this.lastFunction.getName(), this.lastFunction);
		this.visit(ctx.parameters());
		
		this.lastFunction = null;
		
		return null;
	}
	
	@Override
	public Void visitMethodDefinition(MethodDefinitionContext ctx) {
		String prefix = this.main.getPrefixer().getForClass(ctx.visibility, this.scopeName, this.className);
		String name = ctx.methodName.getText();
		String returnType = this.main.visit(ctx.returnType());
		Visibility visibility = this.main.getVisibility(ctx.visibility);
		
		this.lastFunction = this.defineFunction(
			ctx.methodName, prefix, name, returnType, visibility, true
		);
		
		this.functions.put(this.lastFunction.getName(), this.lastFunction);
		
		this.visit(ctx.parameters());
		
		this.lastFunction = null;
		
		return null;
	}
	
	@Override
	public Void visitClassDefinition(ClassDefinitionContext ctx) {
		String prevClassName = this.className;
		
		this.className = ctx.className.getText();
		
		for (ClassStatementsContext classStat : ctx.classStatements()) {
			this.visit(classStat);
		}
		
		this.className = prevClassName;
		
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
