package com.ruke.vrjassc.vrjassc.symbol;

import java.util.Collection;
import java.util.Stack;

import org.antlr.v4.runtime.Token;

public class FunctionSymbol extends ScopeSymbol {

	protected Stack<Symbol> params;
	
	protected Symbol _this;
	
	public FunctionSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
		this.params = new Stack<Symbol>();
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
