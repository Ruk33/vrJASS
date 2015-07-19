package com.ruke.vrjassc.vrjassc.symbol;

import java.util.HashMap;

import org.antlr.v4.runtime.Token;

public class Symbol {

	/**
	 * 
	 */
	protected String name;

	/**
	 * For variables, its the type (unit, real, integer, etc.) For functions,
	 * its the return type
	 */
	protected Type type;

	/**
	 * 
	 */
	protected HashMap<Modifier, Boolean> modifiers;

	/**
	 * 
	 */
	protected Scope scope;
	
	/**
	 * Where it is being defined
	 */
	protected Token token;

	public Symbol(String name, Scope scope, Token token) {
		this.name = name;
		this.modifiers = new HashMap<Modifier, Boolean>();
		this.scope = scope;
		this.token = token;
	}
	
	public String getName() {
		return this.name;
	}

	public Symbol setType(Type type) {
		this.type = type;
		return this;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public Symbol setModifier(Modifier modifier, boolean value) {
		this.modifiers.put(modifier, value);
		return this;
	}
	
	public boolean hasModifier(Modifier whichOne) {
		return this.modifiers.getOrDefault(whichOne, false);
	}
	
	public Scope getParentScope() {
		return this.scope;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public boolean isTypeCompatible(Symbol symbol) {
		if (this.getType() == null || symbol.getType() == null) return false;
		return this.getType().isTypeCompatible(symbol);
	}

	public Token getToken() {
		return this.token;
	}

}
