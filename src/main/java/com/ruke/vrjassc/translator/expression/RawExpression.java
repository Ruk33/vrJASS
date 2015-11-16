package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class RawExpression extends Expression {

	protected String expression;
	protected Symbol symbol;
	
	public RawExpression(String expression, Symbol symbol) {
		this.expression = expression;
		this.symbol = symbol;
	}
	
	public RawExpression(String expression) {
		this(expression, null);
	}
	
	@Override
	public Symbol getSymbol() {
		return this.symbol;
	}
	
	@Override
	public String translate() {
		return this.expression;
	}

}
