package com.ruke.vrjassc.vrjassc.util;

import java.util.LinkedList;

import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.MethodSymbol;
import com.ruke.vrjassc.vrjassc.symbol.PropertySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class Prefix {

	public static String build(Symbol symbol) {
		if (symbol instanceof LocalVariableSymbol == false) {
			boolean isMethod = symbol instanceof MethodSymbol;
			boolean isProperty = symbol instanceof PropertySymbol;
			
			LinkedList<String> e = new LinkedList<String>();
			
			while (symbol.getParentScope() != null) {
				e.addFirst(symbol.getName());
				symbol = (Symbol) symbol.getParentScope();
			}
			
			if (isMethod || isProperty) {
				e.addFirst("struct");
			}
			
			if (!e.isEmpty()) {
				return String.join("_", e);
			}
		}
		
		return symbol.getName();
	}

}
