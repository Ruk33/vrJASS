package com.ruke.vrjassc.vrjassc.symbol;

public class InitTrigSymbol extends FunctionSymbol {

	public InitTrigSymbol(String name, Scope scope) {
		super(name, scope, null);
	}
	
	public boolean mustBeDeleted() {
		return this.getChilds().isEmpty();
	}

}
