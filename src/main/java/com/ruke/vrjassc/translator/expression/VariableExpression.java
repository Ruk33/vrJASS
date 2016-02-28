package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.UserTypeSymbol;
import com.ruke.vrjassc.vrjassc.util.Prefix;

public class VariableExpression extends Expression {

	protected Symbol variable;
	protected Expression index;
	
	public VariableExpression(Symbol variable, Expression index) {
		this.variable = variable;
		this.index = index;
		
		if (this.index != null) {
			this.index.setParent(this);
		}
	}
	
	public Expression getIndex() {
		return this.index;
	}
	
	@Override
	public String translate() {
		String name = Prefix.build(this.getSymbol());
		
		if (this.index != null && this.getParent() instanceof ChainExpression == false) {
			return name + "[" + this.index.translate() + "]";
		}
		
		return name;
	}

	@Override
	public Symbol getSymbol() {
		return this.variable;
	}

}
