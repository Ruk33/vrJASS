package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

/**
 * @deprecated
 * @author Ruke
 *
 */
public class PropertySymbol extends Symbol {

	public PropertySymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
		this.setModifier(Modifier.MEMBER, true);
	}

}
