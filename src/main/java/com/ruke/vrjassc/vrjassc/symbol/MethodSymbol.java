package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

/**
 * @deprecated
 * @author Ruke
 *
 */
public class MethodSymbol extends FunctionSymbol {

	private LocalVariableSymbol _this;
	
	public MethodSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
		
		this._this = new LocalVariableSymbol("this", this, null);
		
		this.setModifier(Modifier.MEMBER, true);
	}
	
	@Override
	public boolean hasAccess(Symbol symbol) {
		if (symbol instanceof PropertySymbol || symbol instanceof MethodSymbol) {
			return this.getParentScope().hasAccess(symbol);
		}
		
		return super.hasAccess(symbol);
	}
	
	@Override
	public Symbol resolve(Scope requesting, String name) {
		if (name.equals("this") && !this.hasModifier(Modifier.STATIC)) {
			return this._this;
		}
		
		return super.resolve(requesting, name);
	}

}
