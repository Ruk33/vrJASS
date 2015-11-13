package com.ruke.vrjassc.vrjassc.util;

import java.util.LinkedList;

import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.MethodSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.PropertySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class Prefix {

	public static String build(Symbol symbol) {
		if (!symbol.hasModifier(Modifier.LOCAL)) {
			LinkedList<String> e = new LinkedList<String>();
			Symbol parent = symbol;
			
			while (parent.getParentScope() != null) {
				e.addFirst(parent.getName());
				parent = (Symbol) parent.getParentScope();
			}
			
			if (symbol.hasModifier(Modifier.MEMBER)) {
				e.addFirst("struct");
			}
			
			if (!e.isEmpty()) {
				return String.join("_", e);
			}
		}
		
		return symbol.getName();
	}

}
