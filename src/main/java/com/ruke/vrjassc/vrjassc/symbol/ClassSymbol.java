package com.ruke.vrjassc.vrjassc.symbol;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;

import org.antlr.v4.runtime.Token;

public class ClassSymbol extends InterfaceSymbol {
	
	protected boolean _abstract;
	protected Collection<Symbol> abstractMethods;
	protected Collection<Symbol> unimplementedAbstractMethods;
	protected ClassSymbol _super;
	protected LinkedList<Symbol> properties;
	
	public ClassSymbol(String name, boolean isAbstract, Visibility visibility, Symbol parent, Token token) {
		super(name, visibility, parent, token);
		
		this._abstract = isAbstract;
		this.abstractMethods = new Stack<Symbol>();
		this.unimplementedAbstractMethods = new Stack<Symbol>();
		this.primitiveType = PrimitiveType.CLASS;
		this.properties = new LinkedList<Symbol>();
	}
	
	public ClassSymbol getSuper() {
		return this._super;
	}
	
	public LinkedList<Symbol> getProperties() {
		return this.properties;
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
			
			boolean ok;
			int i;
			Stack<Symbol> abstractMethodParams;
			Stack<Symbol> resolvedParams;
			
			for (Symbol abstractMethod : this.getAbstractMethods()) {
				resolved = this.resolve(
					abstractMethod.getName(),
					PrimitiveType.FUNCTION,
					false
				);
				
				if (resolved == null) {
					continue;
				}
				
				if (!((ClassMemberSymbol) resolved).isAbstract()) {
					i = 0;
					ok = true;
					
					abstractMethodParams = ((FunctionSymbol) abstractMethod).getParams();
					resolvedParams = ((FunctionSymbol) resolved).getParams();
					
					if (resolvedParams.size() == abstractMethodParams.size()) {
						for (Symbol param : abstractMethodParams) {
							if (!resolvedParams.get(i).getType().equals(param.getType())) {
								ok = false;
								break;
							}
							
							i++;
						}
					}
					
					if (!resolved.getType().equals(abstractMethod.getType())) {
						ok = false;
					}
					
					if (ok) {
						continue;
					}
				}
				
				this.unimplementedAbstractMethods.add(resolved);
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
		
		if (child instanceof PropertySymbol) {
			this.properties.add(child);
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
