package com.ruke.vrjassc.vrjassc.symbol;

import java.util.Collection;
import java.util.LinkedList;

import org.antlr.v4.runtime.Token;

public abstract class InitializerContainerSymbol extends Symbol {

	public InitializerContainerSymbol(String name, String type,
			PrimitiveType primitiveType, Visibility visibility, Symbol parent,
			Token token) {
		super(name, type, primitiveType, visibility, parent, token);
	}

	protected Symbol initializer;
	
	public Collection<Symbol> getSymbolsToLoadFirst() {
		return new LinkedList<Symbol>();
	}
	
	public Symbol setInitializer(Symbol initializer) {
		this.initializer = initializer;
		return this;
	}
	
	public Symbol getInitializer() {
		return this.initializer;
	}
	
}
