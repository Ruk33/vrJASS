package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class MethodSymbol extends FunctionSymbol {

	public MethodSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
	}
	
	@Override
	public boolean hasAccess(Symbol symbol) {
		if (symbol instanceof PropertySymbol || symbol instanceof MethodSymbol) {
			return this.getParentScope().hasAccess(symbol);
		}
		
		return super.hasAccess(symbol);
	}
	
	@Override
	public Symbol resolve(Scope requesting, String name) {
		if (name.equals("this")) {
			return (Symbol) this.getParentScope();
		}
		
		return super.resolve(requesting, name);
	}

}
