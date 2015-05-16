package com.ruke.vrjassc.vrjassc.symbol;

import java.util.LinkedList;

import org.antlr.v4.runtime.Token;

public class LibrarySymbol extends Symbol {

	protected LinkedList<Symbol> requirements;
	protected Symbol initializer;
	
	public LibrarySymbol(String name, Symbol parent, Token token) {
		super(name, null, PrimitiveType.LIBRARY, Visibility.PUBLIC, parent, token);
		this.requirements = new LinkedList<Symbol>();
	}
	
	@Override
	public Symbol addChild(Symbol child) {
		if (child instanceof LibrarySymbol) {
			this.requirements.add(child);
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
	
	public LinkedList<Symbol> getRequirements() {
		return this.requirements;
	}

}
