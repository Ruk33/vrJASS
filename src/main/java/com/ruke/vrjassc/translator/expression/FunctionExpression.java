package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.MutualRecursion;
import com.ruke.vrjassc.vrjassc.util.Prefix;

public class FunctionExpression extends Expression {
	
	protected Symbol function;
	protected boolean isCode;
	protected ExpressionList args;
	public boolean useOverrideName;
	
	public FunctionExpression(Symbol function, boolean isCode, ExpressionList args) {
		this.function = function;
		this.isCode = isCode;
		this.args = args;
		this.args.setParent(this);
	}
	
	@Override
	public void setParent(Expression parent) {
		parent.registerFunctionUsage(this.getSymbol());
		super.setParent(parent);
	}
	
	public ExpressionList getArguments() {
		return this.args;
	}
	
	@Override
	public String translate() {
		Symbol symbol = this.getSymbol();
		
		if (this.getSymbol().hasModifier(Modifier.OVERRIDE)) {
			if (!this.useOverrideName) {
				symbol = ((FunctionSymbol) this.getSymbol()).getOriginal();
			}
		}
		
		String name = Prefix.build(symbol);
		
		if (this.getParent() != null) {
			MutualRecursion recursion = this.getParent().getMutualRecursion(this.getSymbol());
			
			if (recursion != null) {
				name = recursion.getPrefix();
			}
		}
		
		if (this.getSymbol().hasModifier(Modifier.OVERRIDE)) {
			if (!this.useOverrideName) {
				name += "_vtype";
			}
		}
		
		if (this.isCode) {
			return "function " + name;
		}
		
		return name + "(" + this.args.translate() + ")";
	}

	@Override
	public Symbol getSymbol() {
		return this.function;
	}

}
