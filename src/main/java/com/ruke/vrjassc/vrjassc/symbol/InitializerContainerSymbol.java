package com.ruke.vrjassc.vrjassc.symbol;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;

import org.antlr.v4.runtime.Token;

public abstract class InitializerContainerSymbol extends Symbol {

	protected Stack<Symbol> initializers;
	
	public InitializerContainerSymbol(String name, String type,
			PrimitiveType primitiveType, Visibility visibility, Symbol parent,
			Token token) {
		super(name, type, primitiveType, visibility, parent, token);
		this.initializers = new Stack<Symbol>();
	}

	protected Symbol initializer;
	
	public Collection<Symbol> getSymbolsToLoadFirst() {
		return new LinkedList<Symbol>();
	}
	
	@Override
	public Symbol addChild(Symbol child) {
		// implementing a module maybe?, if we dont have an initializer yet
		// use the one that the child has
		if (child instanceof InitializerContainerSymbol) {
			if (this.getInitializer() == null) {
				this.setInitializer(
					((InitializerContainerSymbol) child).getInitializer()
				);
			}
		}
		
		return super.addChild(child);
	}
	
	public Symbol setInitializer(Symbol initializer) {
		this.initializer = initializer;
		return this;
	}
	
	public Symbol getInitializer() {
		return this.initializer;
	}
	
}
