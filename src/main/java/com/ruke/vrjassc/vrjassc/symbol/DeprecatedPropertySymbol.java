package com.ruke.vrjassc.vrjassc.symbol;

public class DeprecatedPropertySymbol extends Symbol {

	public DeprecatedPropertySymbol(VariableSymbol variable) {
		super(variable.getName(), variable.getParentScope(), variable.getToken());
	}

}
