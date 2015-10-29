package com.ruke.vrjassc.translator;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Stack;

import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class PropertyInitializatorTranslator {

	protected Hashtable<Symbol, Collection<Symbol>> properties = new Hashtable<Symbol, Collection<Symbol>>();
	protected DefaultPropertyValueTranslator valueTranslator = new DefaultPropertyValueTranslator();
	
	public String getFunctionName(Symbol _class) {
		return "initializator";
	}
	
	public void setDefaultValue(Symbol _class, Symbol property, String value) {
		if (this.properties.containsKey(_class) == false) {
			this.properties.put(_class, new Stack<Symbol>());
		}
		
		this.properties.get(_class).add(property);
		this.valueTranslator.setDefaultValue(property, value);
	}
	
	public String getTranslation(Symbol _class, ChainExpressionTranslator ct) {
		StringBuilder result = new StringBuilder();
		Symbol instance = new LocalVariableSymbol("this", null, null);
		
		instance.setType(_class.getType());
		
		result.append("function ");
		result.append(this.getFunctionName(_class));
		result.append(" takes integer this returns nothing");
		result.append("\n");
		
		for (Symbol property : this.properties.get(_class)) {
			result.append(this.valueTranslator.getTranslation(instance, property, ct));
			result.append("\n");
		}
		
		result.append("endfunction");
		
		return result.toString();
	}
	
}
