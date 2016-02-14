package com.ruke.vrjassc.vrjassc.symbol;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.Token;

public class InterfaceSymbol extends UserTypeSymbol {

	protected Set<ClassSymbol> implementedBy = new HashSet<ClassSymbol>();
	
	public InterfaceSymbol(String name, int vtype, Scope scope, Token token) {
		super(name, vtype, scope, token);
	}
	
	public void implementedBy(ClassSymbol symbol) {
		this.implementedBy.add(symbol);
	}
	
	public Collection<ClassSymbol> getImplementations() {
		return this.implementedBy;
	}
	
}
