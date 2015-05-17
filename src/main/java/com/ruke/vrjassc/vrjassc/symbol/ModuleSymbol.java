package com.ruke.vrjassc.vrjassc.symbol;

import java.util.Collection;

import org.antlr.v4.runtime.Token;

public class ModuleSymbol extends InitializerContainerSymbol {

	protected String value;
	
	public ModuleSymbol(String name, Visibility visibility, Symbol parent, Token token) {
		super(name, null, PrimitiveType.MODULE, visibility, parent, token);
	}

}
