package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class VariableExpression extends Expression {

	protected Symbol variable;
	protected Expression index;
	
	public VariableExpression(Symbol variable, Expression index) {
		this.variable = variable;
		this.index = index;
	}
	
	public Expression getIndex() {
		return this.index;
	}
	
	@Override
	public String translate() {
		String name = this.variable.getName();
		
		if (this.index != null) {
			return name + "[" + this.index.translate() + "]";
		}
		
		return name;
	}

	@Override
	public Symbol getSymbol() {
		return this.variable;
	}

}
