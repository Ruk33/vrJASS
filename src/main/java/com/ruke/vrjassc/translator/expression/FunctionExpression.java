package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.MethodSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Scope;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.MutualRecursion;

public class FunctionExpression extends Expression {
	
	protected Symbol function;
	protected boolean isCode;
	protected ExpressionList args;
	
	public FunctionExpression(Symbol function, boolean isCode, ExpressionList args) {
		this.function = function;
		this.isCode = isCode;
		this.args = args;
	}
	
	@Override
	public void setParent(Expression parent) {
		parent.registerFunctionUsage(this.getSymbol());
		super.setParent(parent);
	}
	
	public ExpressionList getArguments() {
		return this.args;
	}
		
	protected String getName() {
		String name = this.getSymbol().getName();
		
		if (this.getSymbol() instanceof MethodSymbol) {
			Scope _class = this.getSymbol().getParentScope();
			name = String.format("struct_%s_%s", _class.getName(), name);
		}
		
		return name;
	}
	
	@Override
	public String translate() {
		MutualRecursion recursion = this.getParent().getMutualRecursion(this.getSymbol());
		String name = this.getName();
		
		if (recursion != null) {
			name = recursion.getPrefix();
		}
		
		return name + "(" + this.args.translate() + ")";
	}

	@Override
	public Symbol getSymbol() {
		return this.function;
	}

}
