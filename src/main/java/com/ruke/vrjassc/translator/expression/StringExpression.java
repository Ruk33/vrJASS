package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class StringExpression extends Expression {

	protected String string;
	protected Symbol stringType;
	
	public StringExpression(String string, Symbol stringType) {
		this.string = string;
		this.stringType = stringType;
	}

	@Override
	public Symbol getSymbol() {
		return this.stringType;
	}
	
	@Override
	public String translate() {
		return this.string;
	}

}
