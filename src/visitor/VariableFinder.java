package visitor;

import java.util.HashMap;

import exception.AlreadyDefinedVariableException;
import symbol.FunctionSymbol;
import symbol.VariableSymbol;
import symbol.Visibility;
import antlr4.vrjassBaseVisitor;
import antlr4.vrjassParser.FunctionDefinitionContext;
import antlr4.vrjassParser.GlobalVariableStatementContext;
import antlr4.vrjassParser.LibraryDefinitionContext;
import antlr4.vrjassParser.LibraryStatementsContext;
import antlr4.vrjassParser.LocalVariableStatementContext;
import antlr4.vrjassParser.ParameterContext;

public class VariableFinder extends vrjassBaseVisitor<Void> {

	protected MainVisitor main;
	
	protected HashMap<String, VariableSymbol> globalVariables;
	protected HashMap<String, HashMap<String, VariableSymbol>> localVariables;
	
	protected String scopeName;
	protected String funcName;
	
	public VariableFinder(MainVisitor main) {
		this.main = main;
		this.globalVariables = new HashMap<String, VariableSymbol>();
		this.localVariables = new HashMap<String, HashMap<String, VariableSymbol>>();
	}
	
	public VariableSymbol get(String funcName, String variableName, String scopeName) {
		VariableSymbol variable = null;
		
		if (funcName != null) {
			if (this.localVariables.containsKey(funcName)) {
				variable = this.localVariables.get(funcName).get(variableName);
			}
		}
		
		if (variable == null) {
			String privateName = this.main.getPrefixedName(scopeName, variableName, false);
			String publicName = this.main.getPrefixedName(scopeName, variableName, true);
			
			variable = this.globalVariables.get(privateName);
			
			if (variable == null) {
				variable = this.globalVariables.get(publicName);
			}
			
			if (variable == null) {
				variable = this.globalVariables.get(variableName);
			}
		}
		
		return variable;
	}
	
	public VariableSymbol get(FunctionSymbol function, String variableName, String scopeName) {
		String funcName = null;
		
		if (function != null) {
			funcName = function.getName();
		}
		
		return this.get(funcName, variableName, scopeName);
	}
	
	protected VariableSymbol put(String funcName, VariableSymbol variable) {
		VariableSymbol alreadyDefined = null;
		
		if (funcName == null) {
			alreadyDefined = this.globalVariables.get(variable.getName());
		} else {
			alreadyDefined = this.localVariables.get(funcName).get(variable.getName());
		}
		
		if (alreadyDefined != null) {
			throw new AlreadyDefinedVariableException(variable.getToken(), alreadyDefined);
		}
		
		if (funcName == null) {
			this.globalVariables.put(variable.getName(), variable);
		} else {
			this.localVariables.get(funcName).put(variable.getName(), variable);
		}
		
		return variable;
	}
	
	@Override
	public Void visitGlobalVariableStatement(GlobalVariableStatementContext ctx) {
		String prefix = this.main.getPrefix(ctx.visibility, this.scopeName);
		String variableName = prefix + ctx.varName.getText();
		String variableType = ctx.variableType().getText();
		boolean isArray = ctx.array != null;
		Visibility visibility = this.main.getVisibility(ctx.visibility);
		
		VariableSymbol variable = new VariableSymbol(
			variableName,
			variableType,
			isArray,
			null,
			ctx.varName,
			visibility,
			this.scopeName
		);
		
		this.put(null, variable);
		
		return null;
	}
	
	@Override
	public Void visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		String variableName = ctx.varName.getText();
		String variableType = ctx.variableType().getText();
		boolean isArray = ctx.array != null;
		
		VariableSymbol variable = new VariableSymbol(
			variableName,
			variableType,
			isArray,
			null,
			ctx.varName,
			Visibility.PRIVATE,
			this.scopeName
		);
		
		this.put(this.funcName, variable);
		
		return null;
	}
	
	@Override
	public Void visitParameter(ParameterContext ctx) {
		String variableName = ctx.ID().getText();
		String variableType = ctx.variableType().getText();
		VariableSymbol variable = new VariableSymbol(
			variableName,
			variableType,
			false,
			null,
			ctx.ID().getSymbol(),
			Visibility.PRIVATE,
			this.scopeName
		);
		
		this.put(this.funcName, variable);
		
		return null;
	}
	
	@Override
	public Void visitFunctionDefinition(FunctionDefinitionContext ctx) {
		String prevFuncName = this.funcName;
		
		this.funcName = ctx.functionName.getText();
		
		if (!this.localVariables.containsKey(this.funcName)) {
			this.localVariables.put(
				this.funcName,
				new HashMap<String, VariableSymbol>()
			);
		}
		
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
