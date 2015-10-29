package com.ruke.vrjassc.translator.expression;

import java.util.LinkedList;
import com.ruke.vrjassc.translator.ChainExpressionTranslator;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class ChainExpression extends Expression {

	protected ChainExpressionTranslator chainTranslator;
	protected LinkedList<Expression> expressions;
	protected Expression value;
	
	public ChainExpression() {
		this.chainTranslator = new ChainExpressionTranslator();
		this.expressions = new LinkedList<Expression>();
	}
	
	public void append(Expression expression, String key) {
		this.expressions.add(expression);
	}
	
	public void setValue(Expression value) {
		this.value = value;
	}
	
	public String translate() {
		Expression last = this.expressions.getLast();
		
		if (last instanceof FunctionExpression) {
			this.expressions.removeLast();
			((FunctionExpression) last).getArguments().getList().addFirst(this);
			
			String result = last.translate();
			
			((FunctionExpression) last).getArguments().getList().removeFirst();
			this.expressions.add(last);
			
			return result;
		} else {
			if (this.value != null) {
				this.chainTranslator.setValue(this.value.translate());
			}
		}
		
		for (Expression expression : this.expressions) {
			this.chainTranslator.append(expression.getSymbol(), null, null);
		}
		
		return this.chainTranslator.build();
	}

	@Override
	public Symbol getSymbol() {
		if (this.expressions.isEmpty()) {
			return null;
		}
		
		return this.expressions.getLast().getSymbol();
	}
	
}