package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.Collection;

public class LibrarySymbol extends ScopeSymbol implements InitializerContainer {

	protected Collection<LibrarySymbol> requirements = new ArrayList<LibrarySymbol>();
	protected Symbol initializer;
	
	public LibrarySymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
	}
	
	public void defineRequirement(LibrarySymbol requirement) {
		this.requirements.add(requirement);
	}
	
	public void setInitializer(Symbol initializer) {
		this.initializer = initializer;
	}

	public Symbol getInitializer() {
		return this.initializer;
	}

	@Override
	public Collection<InitializerContainer> getInitializersToLoadFirst() {
		Collection<InitializerContainer> loadFirst = new ArrayList<InitializerContainer>();
		
		loadFirst.addAll(this.requirements);
		
		return loadFirst;
	}

}
