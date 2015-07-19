package com.ruke.vrjassc.vrjassc.symbol;

import java.util.Stack;

import org.antlr.v4.runtime.Token;

public class FunctionSymbol extends ScopeSymbol {

	protected Stack<Symbol> params;
	
	public FunctionSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
		this.params = new Stack<Symbol>();
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
