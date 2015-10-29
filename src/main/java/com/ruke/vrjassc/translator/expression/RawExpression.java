package com.ruke.vrjassc.translator.expression;

public class RawExpression extends Expression {

	protected String expression;
	
	public RawExpression(String expression) {
		this.expression = expression;
	}
	
	@Override
	public String translate() {
		return this.expression;
	}

}
