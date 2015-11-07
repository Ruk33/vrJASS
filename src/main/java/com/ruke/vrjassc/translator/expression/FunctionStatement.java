package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.InitTrigSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class FunctionStatement extends Statement {

	protected Expression function;
	
	public FunctionStatement(Expression function) {
		this.function = function;
		this.function.setParent(this);
	}

	@Override
	public String translate() {
		if (this.getSymbol() instanceof InitTrigSymbol) {
			if (((InitTrigSymbol) this.getSymbol()).mustBeDeleted()) {
				return "";
			}
		}
		
		return "call " + this.function.translate();
	}
	
	@Override
	public Symbol getSymbol() {
		return this.function.getSymbol();
	}
	
}
