package com.ruke.vrjassc.translator.expression;

public class NotExpression extends Expression {

	protected Expression expression;
	
	public NotExpression(Expression expression) {
		this.expression = new ParenthesisExpression(new BooleanExpression(expression));
		this.expression.setParent(this);
	}
	
	@Override
	public String translate() {
		return "not " + this.expression.translate();
	}

}
