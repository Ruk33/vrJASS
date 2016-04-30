package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

import java.util.HashMap;

public class Symbol {

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

	public Modifier getVisibility() {
		if (this.hasModifier(Modifier.PUBLIC)) {
			return Modifier.PUBLIC;
		} else if (this.hasModifier(Modifier.PROTECTED)) {
			return Modifier.PROTECTED;
		}

		return Modifier.PRIVATE;
	}
	
	public String getName() {
		return this.name;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
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
		return this.getType().isTypeCompatible(symbol);
	}

	public Token getToken() {
		return this.token;
	}

}
