package com.ruke.vrjassc.vrjassc.symbol;

import java.util.Collection;
import java.util.LinkedList;

import org.antlr.v4.runtime.Token;

public class LibrarySymbol extends InitializerContainerSymbol {

	protected LinkedList<Symbol> requirements;
	
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
	
	public LinkedList<Symbol> getRequirements() {
		return this.requirements;
	}

	@Override
	public Collection<Symbol> getSymbolsToLoadFirst() {
		return this.getRequirements();
	}

}
