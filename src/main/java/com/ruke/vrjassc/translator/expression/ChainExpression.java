package com.ruke.vrjassc.translator.expression;

import java.util.LinkedList;

import com.ruke.vrjassc.translator.ChainExpressionTranslator;
import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.UserTypeSymbol;
import com.ruke.vrjassc.vrjassc.util.Prefix;

public class ChainExpression extends Expression {

	protected ChainExpressionTranslator chainTranslator;
	protected LinkedList<Expression> expressions;
	protected Expression value;
	
	public ChainExpression() {
		this.chainTranslator = new ChainExpressionTranslator();
		this.expressions = new LinkedList<Expression>();
	}
	
	public void setHashtableName(String name) {
		this.chainTranslator.setHashtableName(name);
	}
		
	public void append(Expression expression, String key) {
		if (expression instanceof ChainExpression) {
			for (Expression ch : ((ChainExpression) expression).expressions) {
				this.append(ch, null);
			}
			
			return;
		}
		
		expression.setParent(this);
		this.expressions.add(expression);
	}
	
	public void setValue(Expression value) {
		this.value = value;
	}
	
	public String translate() {
		Expression last = this.expressions.getLast();
		
		if (last instanceof FunctionExpression) {
			this.expressions.removeLast();
			
			boolean isStatic = last.getSymbol().hasModifier(Modifier.STATIC);
			FunctionExpression func = ((FunctionExpression) last);
			
			if (!isStatic) {
				func.getArguments().getList().addFirst(this);
			}
			
			String result = last.translate();
			
			if (!isStatic) {
				func.getArguments().getList().removeFirst();
			}
			
			this.expressions.add(last);
			
			return result;
		} else {
			if (this.value != null) {
				String val = this.value.translate();
				
				if (val.equals("null") && last.getSymbol().getType() instanceof UserTypeSymbol) {
					val = "0";
				}
				
				this.chainTranslator.setValue(val);
			}
		}
		
		VariableExpression varExpr;
		String name;
		String index;
		
		for (Expression expression : this.expressions) {
			index = null;
			
			if (expression instanceof VariableExpression) {
				varExpr = ((VariableExpression) expression);
				
				if (varExpr.getIndex() != null) {
					index = varExpr.getIndex().translate();
				}
			}
			
			if (expression.getSymbol() == null) {
				name = expression.translate();
			} else {
				name = Prefix.build(expression.getSymbol());
			}
			
			this.chainTranslator.append(
				expression.getSymbol(),
				index,
				name
			);
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
