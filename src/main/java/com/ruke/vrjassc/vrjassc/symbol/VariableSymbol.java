package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class VariableSymbol extends Symbol {

	public VariableSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
	}

}
