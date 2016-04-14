package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

import java.util.HashMap;
import java.util.Map;

public class ScopeSymbol extends Symbol implements Scope {

	protected Map<String, Symbol> childs;
	protected Scope enclosingScope;
	
	public ScopeSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
		this.childs = new HashMap<String, Symbol>();
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
	 * @param symbol
	 * @return defined symbol
	 */
	@Override
	public Symbol define(Symbol symbol) {
		if (symbol != null) {
			this.childs.put(symbol.getName(), symbol);
			
			if (symbol.scope == null) {
				symbol.setScope(this);
			}
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
		if (symbol == this) {
			return true;
		}
		
		if (symbol.hasModifier(Modifier.PUBLIC)) {
			return true;
		}
		
		if (symbol.getParentScope() == this.getParentScope()) {
			return true;
		}
		
		if (this == symbol.getParentScope()) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public Symbol resolve(Scope requesting, String name) {		
		Symbol resolved = this.childs.get(name);
			
		if (resolved == null || !requesting.hasAccess(resolved)) {
			resolved = null;
			
			if (name.equals(this.getName())) {
				return this;
			}
			
			if (this.getParentScope() != null) {
				return this.getParentScope().resolve(requesting, name);
			}
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
