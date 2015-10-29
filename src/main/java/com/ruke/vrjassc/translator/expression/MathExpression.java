package com.ruke.vrjassc.translator.expression;

public class MathExpression extends Expression {

	public static enum Operator {
		MULT	{ public String toString() { return "*"; } },
		DIV		{ public String toString() { return "/"; } },
		PLUS	{ public String toString() { return "+"; } },
		MINUS	{ public String toString() { return "-"; } },
	};
	
	protected Expression a;
	protected Operator operator;
	protected Expression b;
	
	public MathExpression(Expression a, Operator operator, Expression b) {
		this.a = a;
		this.operator = operator;
		this.b = b;
	}
	
	@Override
	public String translate() {
		return this.a.translate() + this.operator + this.b.translate();
	}
	
}
