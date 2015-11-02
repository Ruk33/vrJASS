package com.ruke.vrjassc.translator.expression;

import java.util.ArrayList;
import java.util.Collection;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.MutualRecursion;

public abstract class Expression {

	protected Expression parent;
	protected Collection<Symbol> usedFunctions = new ArrayList<Symbol>();
	
	public abstract String translate();
	
	public void setParent(Expression parent) {
		this.parent = parent;
	}
	
	public Expression getParent() {
		return this.parent;
	}
	
	public MutualRecursion getMutualRecursion(Symbol function) {
		if (this.getParent() == null) {
			return null;
		}
		
		return this.getParent().getMutualRecursion(function);
	}
	
	public Symbol getSymbol() {
		return null;
	}
	
	public boolean usesFunction(Symbol function) {
		return this.getUsedFunctions().contains(function);
	}
	
	public Collection<Symbol> getUsedFunctions() {
		return this.usedFunctions;
	}
	
	public void registerFunctionUsage(Symbol function) {
		this.getUsedFunctions().add(function);
	}
	
}
