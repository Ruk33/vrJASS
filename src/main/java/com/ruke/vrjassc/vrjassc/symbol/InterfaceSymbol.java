package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class InterfaceSymbol extends ScopeSymbol implements Type {

	public InterfaceSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
	}
	
	@Override
	public Symbol resolve(Scope requesting, String name) {
		if (name.equals(this.getName())) return this;
		
		if (this.getParentScope() != null) { 
			return this.getParentScope().resolve(requesting, name);
		}
		
		return null;
	}

}
