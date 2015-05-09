package com.ruke.vrjassc.vrjassc.symbol;

import java.util.Collection;
import java.util.Stack;

import org.antlr.v4.runtime.Token;

public class ClassSymbol extends InterfaceSymbol {
	
	protected boolean _abstract;
	protected Collection<Symbol> abstractMethods;
	protected Collection<Symbol> unimplementedAbstractMethods;
	protected ClassSymbol _super;
	
	public ClassSymbol(String name, boolean isAbstract, Visibility visibility, Symbol parent, Token token) {
		super(name, visibility, parent, token);
		
		this._abstract = isAbstract;
		this.abstractMethods = new Stack<Symbol>();
		this.unimplementedAbstractMethods = new Stack<Symbol>();
		this.primitiveType = PrimitiveType.CLASS;
	}
	
	public ClassSymbol getSuper() {
		return this._super;
	}
		
	@Override
	public boolean isAbstract() {
		return this._abstract;
	}
	
	@Override
	public Collection<Symbol> getAbstractMethods() {
		return this.abstractMethods;
	}
	
	public Collection<Symbol> getUnimplementedAbstractMethods() {
		this.unimplementedAbstractMethods.clear();
		
		if (!this.isAbstract()) {
			Symbol resolved;
			
			for (Symbol abstractMethod : this.getAbstractMethods()) {
				resolved = this.resolve(
					abstractMethod.getName(),
					PrimitiveType.FUNCTION,
					false
				);
				
				if (resolved == null) {
					continue;
				}
				
				if (((ClassMemberSymbol) resolved).isAbstract()) {
					this.unimplementedAbstractMethods.add(resolved);
				}
			}
		}
		
		return this.unimplementedAbstractMethods;
	}
	
	@Override
	public Symbol addChild(Symbol child) {
		if (child instanceof InterfaceSymbol) {
			this.abstractMethods.addAll(((InterfaceSymbol) child).getAbstractMethods());
			
			if (child instanceof ClassSymbol) {
				this._super = (ClassSymbol) child;
			}
		} else if (child instanceof MethodSymbol) {
			if (((MethodSymbol) child).isAbstract()) {
				this.abstractMethods.add(child);
			}
		}
		
		return super.addChild(child);
	}
	
	public boolean hasDefinedAllAbstractMethods() {
		return this.getUnimplementedAbstractMethods().size() == 0;
	}
	
	@Override
	public String getFullName() {
		return "struct_" + super.getFullName();
	}

}
