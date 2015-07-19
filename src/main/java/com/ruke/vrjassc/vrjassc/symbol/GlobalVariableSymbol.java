package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class GlobalVariableSymbol extends Symbol {

	public GlobalVariableSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
	}

}
