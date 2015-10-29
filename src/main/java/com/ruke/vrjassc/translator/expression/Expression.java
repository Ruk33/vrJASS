package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public abstract class Expression {

	protected Statement parent;
	
	public abstract String translate();
	
	public void setParent(Statement parent) {
		this.parent = parent;
	}
	
	public Statement getParent() {
		return this.parent;
	}
	
	public JassContainer getJassContainer() {
		Expression container = this.getParent();
		
		while (container != null && container instanceof JassContainer == false) {
			container = container.getParent();
		}
		
		if (container == null) {
			return null;
		}
		
		return (JassContainer) container;
	}
	
	public Symbol getSymbol() {
		return null;
	}
	
}
