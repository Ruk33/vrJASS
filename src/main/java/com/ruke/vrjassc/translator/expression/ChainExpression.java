package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.Config;
import com.ruke.vrjassc.translator.ChainExpressionTranslator;
import com.ruke.vrjassc.vrjassc.symbol.*;

import java.util.LinkedList;

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
			boolean isInterface = last.getSymbol().getParentScope() instanceof InterfaceSymbol;
			boolean isOverrided = last.getSymbol().hasModifier(Modifier.OVERRIDE) || !((Overrideable) last.getSymbol()).getImplementations().isEmpty();
			boolean isSuper = this.expressions.getFirst() instanceof SuperExpression;


			FunctionExpression func = ((FunctionExpression) last);
			boolean useOverrideNamePrev = func.useOverrideName;

			if (!isStatic) {
				if (isSuper) {
					func.useOverrideName = true;
				} else if (isInterface || isOverrided) {
					if (this.expressions.getLast().getSymbol() instanceof CastSymbol) {
						int id = ((UserTypeSymbol) this.expressions.getLast().getSymbol().getType()).getTypeId();
						func.getArguments().getList().addFirst(new RawExpression(id));
					} else {
						ChainExpression vtype = new ChainExpression();
						vtype.setHashtableName(this.chainTranslator.getHashtableName());
						
						Expression lastInstance = null;
						
						for (Expression expr : this.expressions) {
							if (expr.getSymbol().getType() instanceof UserTypeSymbol) {
								lastInstance = expr;
							}
						}
						
						vtype.append(lastInstance, null);
						vtype.append(new RawExpression(Config.VTYPE_NAME), null);
						
						func.getArguments().getList().addFirst(vtype);
					}
				}
				
				func.getArguments().getList().addFirst(this);
			} else if (last.getSymbol().getName().equals("allocate")) {
				Expression lastInstance = null;
				
				for (Expression e : this.expressions) {
					if (e.getSymbol() instanceof UserTypeSymbol) {
						lastInstance = e;
					}
				}
				
				func.getArguments().getList().addFirst(
					new RawExpression(
						((UserTypeSymbol) lastInstance.getSymbol().getType()).getTypeId()
					)
				);
			}
			
			String result = last.translate();
			
			if (!isStatic) {
				func.getArguments().getList().removeFirst();
				
				if (!isSuper && (isInterface || isOverrided)) {
					func.getArguments().getList().removeFirst();
				}
			}
			
			this.expressions.add(last);

			func.useOverrideName = useOverrideNamePrev;
			
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
			
			this.chainTranslator.append(
				expression.getSymbol(),
				index,
				expression.translate()
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
