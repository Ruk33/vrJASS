package com.ruke.vrjassc.vrjassc.util;

import java.util.LinkedList;

import com.ruke.vrjassc.vrjassc.symbol.MethodSymbol;
import com.ruke.vrjassc.vrjassc.symbol.PropertySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class Prefix {

	public static String build(Symbol symbol) {
		boolean isMethod = symbol instanceof MethodSymbol;
		boolean isProperty = symbol instanceof PropertySymbol;
		
		if (isProperty || isMethod) {
			LinkedList<String> e = new LinkedList<String>();
			
			while (symbol.getParentScope() != null) {
				e.addFirst(symbol.getName());
				symbol = (Symbol) symbol.getParentScope();
			}
			
			e.addFirst("struct");
			
			return String.join("_", e);
		}
		
		return symbol.getName();
	}

}
