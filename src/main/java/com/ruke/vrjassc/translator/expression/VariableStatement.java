package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.Prefix;
import com.ruke.vrjassc.vrjassc.util.VariableTypeDetector;

public class VariableStatement extends Statement {

	protected Symbol variable;
	protected Expression value;
	
	public VariableStatement(Symbol variable, Expression value) {
		this.variable = variable;
		this.value = value;
		
		if (this.value != null) {
			this.value.setParent(this);
		}
	}
	
	@Override
	public Symbol getSymbol() {
		return this.variable;
	}
	
	@Override
	public String translate() {
		String type = this.variable.getType().getName();
		String name = Prefix.build(this.variable);
		
		if (VariableTypeDetector.isUserType(type)) {
			type = "integer";
		}
		
		String result = type + " " + name;
		
		if (this.variable instanceof LocalVariableSymbol) {
			result = "local " + result;
		}
		
		if (this.value != null) {
			result += "=" + this.value.translate();
		}
		
		return result;
	}
	
}
