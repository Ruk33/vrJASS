package com.ruke.vrjassc.vrjassc.symbol;

import java.util.Collection;
import java.util.HashSet;

import org.antlr.v4.runtime.Token;

public class InterfaceSymbol extends UserTypeSymbol implements Overrideable {

	protected Collection<Symbol> implementedBy = new HashSet<Symbol>();
	
	public InterfaceSymbol(String name, int vtype, Scope scope, Token token) {
		super(name, vtype, scope, token);
	}
	
	public void implementedBy(ClassSymbol symbol) {
		this.implementedBy.add(symbol);
	}
	
	public Collection<Symbol> getImplementations() {
		return this.implementedBy;
	}
	
}
