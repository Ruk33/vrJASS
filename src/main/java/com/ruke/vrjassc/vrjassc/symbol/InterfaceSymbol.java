package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

import java.util.Collection;
import java.util.HashSet;

public class InterfaceSymbol extends UserTypeSymbol implements Overrideable, AbstractMethodContainer {

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

	@Override
	public Collection<Symbol> getAbstractMethods() {
		return this.childs.values();
	}
}
