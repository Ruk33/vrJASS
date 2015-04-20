package visitor;

import java.util.HashMap;

import exception.AlreadyDefinedFunctionException;
import symbol.FunctionSymbol;
import antlr4.vrjassBaseVisitor;
import antlr4.vrjassParser.FunctionDefinitionContext;
import antlr4.vrjassParser.LibraryDefinitionContext;
import antlr4.vrjassParser.LibraryStatementsContext;
import antlr4.vrjassParser.ParameterContext;
import antlr4.vrjassParser.ParametersContext;

public class FunctionFinder extends vrjassBaseVisitor<Void> {

	protected MainVisitor main;
	protected String scopeName;
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
		for (ParameterContext param : ctx.parameter()) {
			this.lastFunction.addParamType(this.main.visit(param.variableType()));
		}
		
		return null;
	}
	
	@Override
	public Void visitFunctionDefinition(FunctionDefinitionContext ctx) {
		String prefix = this.main.getPrefix(ctx.visibility, this.scopeName);
		String name = prefix + ctx.functionName.getText();
		String returnType = this.main.visit(ctx.returnType());
		FunctionSymbol func = this.functions.get(name);
		
		if (func != null) {
			throw new AlreadyDefinedFunctionException(ctx.functionName, func);
		}
		
		this.lastFunction = new FunctionSymbol(name, returnType, prefix, ctx.functionName);
		this.functions.put(name, this.lastFunction);
		this.visit(ctx.parameters());
		this.lastFunction = null;
		
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
