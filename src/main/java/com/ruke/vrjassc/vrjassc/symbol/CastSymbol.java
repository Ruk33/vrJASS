package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class CastSymbol extends Symbol {

	protected Symbol original;
	protected Symbol cast;
	
	public CastSymbol(Symbol original, Symbol cast, Token token) {
		super("", original.getParentScope(), token);

		this.original = original;
		this.cast = cast;
		
		this.setType(cast.getType());
	}
	
	@Override
	public boolean hasModifier(Modifier whichOne) {
		return this.original.hasModifier(whichOne);
	}

}
