package com.ruke.vrjassc.translator.expression;

public class ComparisonExpression extends Expression {

	public static enum Operator {
		EQUAL_EQUAL		{ public String toString() { return "=="; } },
		NOT_EQUAL		{ public String toString() { return "!="; } },
		GREATER			{ public String toString() { return ">"; } },
		GREATER_EQUAL	{ public String toString() { return ">="; } },
		LESS			{ public String toString() { return "<"; } },
		LESS_EQUAL		{ public String toString() { return "<="; } },
	}
	
	protected Expression a;
	protected Operator operator;
	protected Expression b;
	
	public ComparisonExpression(Expression a, Operator operator, Expression b) {
		this.a = a;
		this.operator = operator;
		this.b = b;
	}
	
	@Override
	public String translate() {
		return this.a.translate() + this.operator + this.b.translate();
	}

}
