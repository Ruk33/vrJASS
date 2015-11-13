package com.ruke.vrjassc.vrjassc.symbol;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Token;

public class ScopeSymbol extends Symbol implements Scope {

	protected Map<String, Symbol> childs;
	protected Map<String, Symbol> childsAliases;
	protected Scope enclosingScope;
	
	public ScopeSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
		
		this.childs = new HashMap<String, Symbol>();
		this.childsAliases = new HashMap<String, Symbol>();
	}
	
	@Override
	public Scope getEnclosingScope() {
		return this.scope;
	}

	@Override
	public Scope getParentScope() {
		return this.getEnclosingScope();
	}
	
	/**
	 * 
	 * @param key
	 * @param symbol
	 * @return Defined symbol
	 */
	public Symbol defineAlias(String alias, Symbol symbol) {
		if (symbol != null) {
			this.childsAliases.put(alias, symbol);
			symbol.scope = this;
		}
		
		return symbol;
	}
	
	/**
	 *
	 * @param symbol
	 * @return defined symbol
	 */
	@Override
	public Symbol define(Symbol symbol) {
		if (symbol != null) {
			this.childs.put(symbol.getName(), symbol);
			symbol.scope = this;
		}
		
		return symbol;
	}
	
	/**
	 * Check if symbol has access to other symbol
	 * @param symbol
	 * @return
	 */
	@Override
	public boolean hasAccess(Symbol symbol) {
		if (!symbol.hasModifier(Modifier.PUBLIC) && this != symbol.getParentScope()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public Symbol resolve(Scope requesting, String name) {		
		Symbol resolved = this.childs.getOrDefault(name, this.childsAliases.get(name));
		
		if (resolved == null || !requesting.hasAccess(resolved)) {
			resolved = null;
			
			if (this.getParentScope() != null) {
				return this.getParentScope().resolve(requesting, name);
			}
		}
		
		if (resolved == null && name.equals(this.getName())) {
			resolved = this;
		}
		
		return resolved;
	}
	
	@Override
	public Symbol resolve(String name) {
		return this.resolve(this, name);
	}
	
	public Map<String, Symbol> getChilds() {
		return this.childs;
	}

	@Override
	public Scope toggleEnclosingScope() {
		if (this.enclosingScope == null) {
			this.enclosingScope = this.scope;
			this.scope = null;
		} else {
			this.scope = this.enclosingScope;
			this.enclosingScope = null;
		}
		
		return this;
	}
	
}
