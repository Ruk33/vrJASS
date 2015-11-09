package com.ruke.vrjassc.translator.expression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExpressionList extends Expression {

	protected LinkedList<Expression> expressions = new LinkedList<Expression>();
	
	public LinkedList<Expression> getList() {
		return this.expressions;
	}
	
	public void add(Expression expression) {
		expression.setParent(this);
		this.getList().add(expression);
	}
	
	@Override
	public String translate() {
		List<String> translated = new ArrayList<String>();
		
		for (Expression expression : this.expressions) {
			translated.add(expression.translate());
		}
		
		return String.join(",", translated);
	}

}
