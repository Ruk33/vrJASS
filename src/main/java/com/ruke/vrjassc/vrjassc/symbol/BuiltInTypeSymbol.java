package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class BuiltInTypeSymbol extends Symbol implements Type {

	public BuiltInTypeSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
	}
	
	@Override
	public Type getType() {
		return this;
	}
	
	@Override
	public boolean isTypeCompatible(Symbol symbol) {
		return symbol.getType().getName().equals(this.getName());
	}

}
