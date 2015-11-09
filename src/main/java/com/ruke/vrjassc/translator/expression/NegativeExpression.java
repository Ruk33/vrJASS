package com.ruke.vrjassc.translator.expression;

public class NegativeExpression extends Expression {

	protected Expression expression;
	
	public NegativeExpression(Expression expression) {
		this.expression = expression;
		this.expression.setParent(this);
	}
	
	@Override
	public String translate() {
		return "-" + this.expression.translate();
	}

}
