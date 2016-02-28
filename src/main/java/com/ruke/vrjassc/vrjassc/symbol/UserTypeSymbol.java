package com.ruke.vrjassc.vrjassc.symbol;

import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.Token;

public abstract class UserTypeSymbol extends ScopeSymbol implements Type {

	protected int vtype;
	protected Set<FunctionSymbol> overrides;
	
	public UserTypeSymbol(String name, int vtype, Scope scope, Token token) {
		super(name, scope, token);
		this.vtype = vtype;
		this.overrides = new HashSet<FunctionSymbol>();
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
