package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public abstract class UserTypeSymbol extends ScopeSymbol implements Type {

	protected int vtype;
	
	public UserTypeSymbol(String name, int vtype, Scope scope, Token token) {
		super(name, scope, token);
		this.vtype = vtype;
	}
	
	public int getTypeId() {
		return this.vtype;
	}

	@Override
	public Type getType() {
		return this;
	}
	
	@Override
	public boolean isTypeCompatible(Symbol symbol) {
		if (symbol == null) return false;
		if (symbol.getType() == null) return false;
		
		if ("null".equals(symbol.getType().getName())) {
			return true;
		}
		
		if (this.getName().equals(symbol.getType().getName())) {
			return true;
		}
		
		return false;
	}

}
