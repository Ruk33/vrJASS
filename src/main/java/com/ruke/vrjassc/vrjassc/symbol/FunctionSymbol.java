package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;

public class FunctionSymbol extends ScopeSymbol implements Overrideable {

	protected Collection<Symbol> implementations;
	
	protected FunctionSymbol original;
	
	protected Stack<Symbol> params;
	
	protected Symbol _this;
	
	public FunctionSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
		this.implementations = new HashSet<Symbol>();
		this.params = new Stack<Symbol>();
	}
	
	public Collection<Symbol> getImplementations() {
		return this.implementations;
	}
	
	public void addImplementation(FunctionSymbol f) {
		// Dont override initializers
		if ("onInit".equals(this.getName())) {
			return;
		}

		if (f.hasModifier(Modifier.ABSTRACT)) {
			return;
		}

		this.implementations.add(f);
	}
	
	public void setOriginal(FunctionSymbol f) {
		this.original = f;
	}
	
	public FunctionSymbol getOriginal() {
		if (this.original != null) {
			return this.original.getOriginal();
		}
		
		return this;
	}
	
	@Override
	public boolean hasAccess(Symbol symbol) {
		if (symbol.getParentScope() instanceof ClassSymbol) {
			return this.getParentScope().hasAccess(symbol);
		}

		return super.hasAccess(symbol);
	}
	
	@Override
	public Symbol resolve(Scope requesting, String name) {
		if (name.equals("this")) {
			return this._this;
		}
		
		return super.resolve(requesting, name);
	}
	
	@Override
	public Symbol define(Symbol symbol) {
		if (symbol.getName().equals("this")) {
			this._this = symbol;
			this._this.setModifier(Modifier.LOCAL, true);
			
			return symbol;
		}
		
		return super.define(symbol);
	}
	
	public Symbol defineParam(Collection<Symbol> params) {
		for (Symbol param : params) {
			this.defineParam(param);
		}
		
		return this;
	}
	
	public Symbol defineParam(Symbol param) {
		if (param != null) {
			this.params.push(param);
		}
		
		return this.define(param);
	}
	
	public Stack<Symbol> getParams() {
		return this.params;
	}

}
