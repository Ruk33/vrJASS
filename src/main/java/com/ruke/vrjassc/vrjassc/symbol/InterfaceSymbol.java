package com.ruke.vrjassc.vrjassc.symbol;

import java.util.Collection;
import org.antlr.v4.runtime.Token;

public class InterfaceSymbol extends InitializerContainerSymbol implements ClassMemberSymbol {
	
	public InterfaceSymbol(String name, Visibility visibility, Symbol parent, Token token) {
		super(name, name, PrimitiveType.INTERFACE, visibility, parent, token);
	}
	
	public Collection<Symbol> getAbstractMethods() {
		return this.childs.values();
	}

	@Override
	public boolean isAbstract() {
		return true;
	}

	@Override
	public boolean isStatic() {
		return false;
	}

}
