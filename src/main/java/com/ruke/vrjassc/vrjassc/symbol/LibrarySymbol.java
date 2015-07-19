package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class LibrarySymbol extends ScopeSymbol {

	public LibrarySymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
	}

}
