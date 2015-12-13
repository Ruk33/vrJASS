package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class ModuleSymbol extends ClassSymbol {

	public ModuleSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
	}
	
	@Override
	public void setParentScope(Scope scope) {
		if (this.getParentScope() == null) {
			super.setParentScope(scope);
		}
	}

}
