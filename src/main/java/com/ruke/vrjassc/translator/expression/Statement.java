package com.ruke.vrjassc.translator.expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public abstract class Statement extends Expression {
	
	protected Map<Symbol, Symbol> usedFunctions = new HashMap<Symbol, Symbol>();
	protected FunctionDefinition functionDefinition;
	
	public StatementBody getStatementBody() {
		return (StatementBody) super.getParent();
	}
	
	public void setFunctionDefinition(FunctionDefinition functionDefinition) {
		this.functionDefinition = functionDefinition;
	}
	
	public FunctionDefinition getFunctionDefinition() {
		return this.functionDefinition;
	}
	
	public void registerFunctionUsage(Symbol function) {
		this.usedFunctions.put(function, function);
	}
	
	public boolean usesFunction(Symbol function) {
		return this.usedFunctions.containsKey(function);
	}
	
	public void sort(List<Statement> list, int index) {
		
	}
	
}
