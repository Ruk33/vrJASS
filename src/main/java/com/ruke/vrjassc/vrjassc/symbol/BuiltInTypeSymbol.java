package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.util.TypeCompatibleChecker;
import com.ruke.vrjassc.vrjassc.util.VariableTypeDetector;

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
		return TypeCompatibleChecker.isCompatible(symbol.getType().getName(), this.getName());
	}

}
