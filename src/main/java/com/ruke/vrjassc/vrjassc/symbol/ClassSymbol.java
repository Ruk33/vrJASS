package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class ClassSymbol extends Symbol {
	
	protected ClassSymbol _super;
	
	public ClassSymbol(String name, Visibility visibility, Symbol parent,
			Token token) {
		super(name, name, PrimitiveType.CLASS, visibility, parent, token);
	}
	
	public ClassSymbol getSuper() {
		return this._super;
	}
	
	@Override
	public Symbol addChild(Symbol child) {
		if (child instanceof ClassSymbol) {
			this._super = (ClassSymbol) child;
		}
		
		return super.addChild(child);
	}
	
	public boolean hasDefinedAllAbstractMethods() {
		return true;
	}

	@Override
	public String getFullName() {
		return "struct_" + super.getFullName();
	}

}
