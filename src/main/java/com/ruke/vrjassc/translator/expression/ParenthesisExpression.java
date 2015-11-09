package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class ParenthesisExpression extends Expression {

	protected Expression expression;
	
	public ParenthesisExpression(Expression expression) {
		this.expression = expression;
		this.expression.setParent(this);
	}
	
	@Override
	public String translate() {
		return "(" + this.expression.translate() + ")";
	}

	@Override
	public Symbol getSymbol() {
		return this.expression.getSymbol();
	}
	
}
