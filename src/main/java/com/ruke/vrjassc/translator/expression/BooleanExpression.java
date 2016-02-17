package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Type;
import com.ruke.vrjassc.vrjassc.symbol.UserTypeSymbol;

public class BooleanExpression extends Expression {

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
	
	public BooleanExpression(Expression a, Operator operator, Expression b) {
		this.a = a;
		this.operator = operator;
		this.b = b;
				
		this.a.setParent(this);
		
		if (this.b != null) {
			this.b.setParent(this);
		}
	}
	
	public BooleanExpression(Expression a) {
		this(a, null, null);
	}

	@Override
	public String translate() {
		Type atype = null;
		Type btype = null;
		
		if (this.a.getSymbol() != null) {
			atype = this.a.getSymbol().getType();
		}
		
		if (this.b == null) {
			boolean aIsTrue = this.a.translate().equals("true");
			boolean aIsFalse = this.a.translate().equals("false");
			
			if (aIsTrue || aIsFalse) {
				return this.a.translate();
			}
		
			if (atype != null) {
				if (atype.getName().equals("string")) {
					return "StringLength(" + this.a.translate() + ")!=0";
				} else {
					return new BooleanExpression(
						this.a, 
						Operator.NOT_EQUAL,
						new DefaultValue(atype)
					).translate();
				}
			}
			
			return this.a.translate();
		} else {
			if (this.b.getSymbol() != null) {
				btype = this.b.getSymbol().getType();
			}
		}
		
		if (atype instanceof UserTypeSymbol) {
			if (this.b.translate().equals("null")) {
				return this.a.translate() + this.operator + "0";
			}
		}
		
		if (btype instanceof UserTypeSymbol) {
			if (this.a.translate().equals("null")) {
				return "0" + this.operator + this.b.translate();
			}
		}
		
		return this.a.translate() + this.operator + this.b.translate();
	}

}
