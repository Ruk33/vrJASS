package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.Prefix;
import com.ruke.vrjassc.vrjassc.util.VariableTypeDetector;

public class VariableStatement extends Statement {

	protected Symbol variable;
	protected Expression value;
	
	public VariableStatement(Symbol variable, Expression value) {		
		this.variable = variable;
		
		boolean valueIsNull =
			value == null ||
			value.getSymbol() != null && 
			value.getSymbol().getName().equals("null");
		
		if (valueIsNull) {			
			if (!variable.hasModifier(Modifier.ARRAY) && variable.getType() != null) {
				value = new DefaultValue(variable.getType());
			}
		}
		
		if (value != null) {
			this.value = value;
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
		
		String result = type + " ";
		
		if (this.variable.hasModifier(Modifier.ARRAY)) {
			boolean trueArray = 
				this.variable.hasModifier(Modifier.LOCAL) || 
				this.variable.hasModifier(Modifier.STATIC);
			
			if (trueArray) {
				result += "array ";
			}
		}
		
		result += name;
		
		if (this.variable.hasModifier(Modifier.LOCAL)) {
			result = "local " + result;
		}
		
		if (this.value != null) {
			result += "=" + this.value.translate();
		}
		
		return result;
	}
	
}
