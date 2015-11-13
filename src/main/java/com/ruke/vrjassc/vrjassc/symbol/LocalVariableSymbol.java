package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class LocalVariableSymbol extends Symbol {

	public LocalVariableSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
		this.setModifier(Modifier.PRIVATE, true);
		this.setModifier(Modifier.LOCAL, true);
	}

}
