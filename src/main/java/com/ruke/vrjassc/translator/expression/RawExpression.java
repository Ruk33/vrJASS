package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class RawExpression extends Expression {

	protected String expression;
	protected Symbol symbol;
	protected Expression e;
	
	public RawExpression(String expression, Symbol symbol) {
		this.expression = expression;
		this.symbol = symbol;
	}
	
	public RawExpression(Expression e) { this(null, null); this.e = e; }
	
	public RawExpression(String expression) {
		this(expression, null);
	}
	
	public RawExpression(int expression) {
		this(String.valueOf(expression), null);
	}
	
	@Override
	public Symbol getSymbol() {
		return this.symbol;
	}
	
	@Override
	public String translate() {
		if (this.e != null) return this.e.translate();
		return this.expression;
	}

}
