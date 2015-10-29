package com.ruke.vrjassc.translator;

import java.util.Hashtable;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class DefaultPropertyValueTranslator {
	
	protected Hashtable<Symbol, String> values = new Hashtable<Symbol, String>();
	
	public void setDefaultValue(Symbol property, String value) {
		this.values.put(property, value);
	}
	
	public String getTranslation(Symbol instance, Symbol property, ChainExpressionTranslator ct) {
		String result = "";
		
		if (this.values.containsKey(property)) {
			ct.append(instance, null, null);
			ct.append(property, null, null);
			
			result = ct.buildSetter(this.values.get(property));
		}
		
		return result;
	}

}
