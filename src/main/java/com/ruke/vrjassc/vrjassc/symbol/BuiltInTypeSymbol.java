package com.ruke.vrjassc.vrjassc.symbol;

import com.ruke.vrjassc.vrjassc.util.TypeCompatibleChecker;
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
		if (symbol == null) return false;
		if (symbol.getType() == null) return false;
		
		return TypeCompatibleChecker.isCompatible(
			symbol.getType().getName(), 
			this.getName()
		);
	}

	@Override
	public boolean isUserType() {
		return false;
	}
}
