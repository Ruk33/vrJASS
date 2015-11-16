package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class LogicalExpression extends Expression {

	public enum Operator {
		OR  { public String toString() { return "or"; } },
		AND { public String toString() { return "and"; } },
	};
	
	protected Expression a;
	protected Operator operator;
	protected Expression b;
	
	public LogicalExpression(Expression a, Operator operator, Expression b) {
		this.a = a;
		this.operator = operator;
		this.b = b;
		
		this.a.setParent(this);
		this.b.setParent(this);
	}
	
	@Override
	public Symbol getSymbol() {
		if (this.a.getSymbol() != null) {
			return this.a.getSymbol();
		}
		
		return this.b.getSymbol();
	}
	
	@Override
	public String translate() {
		return this.a.translate() + " " + this.operator + " " + this.b.translate();
	}

}
