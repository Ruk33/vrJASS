package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class ModuleSymbol extends Symbol {

	protected String value;
	
	public ModuleSymbol(String name, Visibility visibility, Symbol parent, Token token) {
		super(name, null, PrimitiveType.MODULE, visibility, parent, token);
	}
	
	public Symbol setValue(String value) {
		this.value = value;
		return this;
	}
	
	public String getValue() {
		return this.value;
	}

}
