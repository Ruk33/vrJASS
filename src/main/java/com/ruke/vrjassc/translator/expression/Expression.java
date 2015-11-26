package com.ruke.vrjassc.translator.expression;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.MutualRecursion;

public abstract class Expression {

	protected Expression parent;
	protected Set<Symbol> usedFunctions = new HashSet<Symbol>();
	
	public abstract String translate();
	
	public void setParent(Expression parent) {
		parent.getUsedFunctions().addAll(this.getUsedFunctions());
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
