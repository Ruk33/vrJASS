package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Type;
import com.ruke.vrjassc.vrjassc.util.VariableTypeDetector;

public class DefaultValue extends Expression {

	protected Expression expression;
	
	public DefaultValue(Type type) {
		String typeName = type.getName();
		String rawValue = "";
		
		if (typeName.equals("integer") || VariableTypeDetector.isUserType(typeName)) {
			rawValue = "0";	
		} else if (typeName.equals("real")) {
			rawValue = "0.0";
		} else if (typeName.equals("string")) {
			rawValue = "\"\"";
		} else if (typeName.equals("boolean")) {
			rawValue = "false";
		} else if (VariableTypeDetector.isHandle(typeName)) {
			rawValue = "null";
		}
		
		this.expression = new RawExpression(rawValue);
	}
	
	@Override
	public String translate() {
		return this.expression.translate();
	}

}
