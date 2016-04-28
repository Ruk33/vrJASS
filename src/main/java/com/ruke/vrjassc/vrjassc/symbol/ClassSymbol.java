package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ClassSymbol extends UserTypeSymbol implements InitializerContainer, AbstractMethodContainer {

	private ClassSymbol _super;
	private Set<InterfaceSymbol> interfaces = new HashSet<InterfaceSymbol>();
	private Set<Symbol> abstracts = new HashSet<Symbol>();
	protected Symbol onInit;
	
	public ClassSymbol(String name, int vtype, Scope scope, Token token) {
		super(name, vtype, scope, token);
		this.setModifier(Modifier.MEMBER, true);
	}
	
	@Override
	public Symbol define(Symbol symbol) {
		if (symbol.getName().equals("onInit")) {
			this.setInitializer(symbol);
		}
		
		if (symbol instanceof InterfaceSymbol) {
			this.interfaces.add((InterfaceSymbol) symbol);
			((InterfaceSymbol) symbol).implementedBy(this);
		}

		if (symbol instanceof FunctionSymbol && symbol.hasModifier(Modifier.ABSTRACT)) {
			this.abstracts.add(symbol);
		}
		
		return super.define(symbol);
	}
	
	@Override
	public Scope getParentScope() {
		if (this.getSuper() != null) {
			return this.getSuper();
		}
		
		return super.getParentScope();
	}
	
	public boolean hasAccessToMember(Symbol member) {
		if (member == null) {
			return false;
		}

		if (member == this) {
			return true;
		}

		if (member.hasModifier(Modifier.PROTECTED)) {
			ClassSymbol _class = this;
			Scope memberParent = member.getParentScope();
			
			while (_class != null) {
				if (memberParent == _class) {
					return true;
				}
				
				_class = _class.getSuper();
			}
			
			return false;
		} else if (member.hasModifier(Modifier.PUBLIC)) {
			return true;
		}

		if (member.getParentScope() == this) {
			return true;
		}

		if (member instanceof ClassSymbol && member.getParentScope() == this.getParentScope()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasAccess(Symbol symbol) {
		if (symbol.getParentScope() instanceof ClassSymbol) {
			return this.hasAccessToMember(symbol);
		}

		return super.hasAccess(symbol);
	}

	@Override
	public Symbol resolve(Scope requesting, String name) {
		Symbol resolved = null;
		
		if (requesting instanceof ClassSymbol) {
			resolved = this.resolveMember((ClassSymbol) requesting, name);
		} 

		if (resolved == null) {
			resolved = super.resolve(requesting, name);
		}
		
		if (resolved == null && this.getEnclosingScope() != null) {
			resolved = this.getEnclosingScope().resolve(requesting, name);
		}
		
		return resolved;
	}
	
	public Symbol resolveMember(ClassSymbol requesting, String name) {
		Symbol member = this.childs.get(name);
		
		if (!requesting.hasAccessToMember(member)) {
			member = null;
			
			if (this.getSuper() != null) {
				member = this.getSuper().resolveMember(requesting, name);
			}
		}
		
		return member;
	}
	
	public ClassSymbol getSuper() {
		return this._super;
	}
	
	public ClassSymbol extendsFrom(ClassSymbol _super) {
		this._super = _super;
		
		for (InterfaceSymbol _interface : _super.interfaces) {
			_interface.implementedBy(this);
		}
		
		
		Symbol o;
		FunctionSymbol original;
		
		for (Symbol f : _super.childs.values()) {
			if (f instanceof FunctionSymbol == false) continue;
			
			if (this.childs.containsKey(f.getName())) {
				o = this.childs.get(f.getName());
				original = ((FunctionSymbol) f).getOriginal();
				
				o.setModifier(Modifier.OVERRIDE, true);
				((FunctionSymbol) o).setOriginal(original);
				original.addImplementation((FunctionSymbol) o);
			}
		}
		
		return this;
	}
	
	@Override
	public boolean isTypeCompatible(Symbol symbol) {
		if (super.isTypeCompatible(symbol)) {
			return true;
		}
		
		for (InterfaceSymbol _interface : this.interfaces) {
			if (_interface.isTypeCompatible(symbol)) {
				return true;
			}
		}
		
		if (this.getSuper() != null) {
			return this.getSuper().isTypeCompatible(symbol);
		}
		
		return false;
	}

	@Override
	public void setInitializer(Symbol initializer) {
		this.onInit = initializer;
	}

	@Override
	public Symbol getInitializer() {
		return this.onInit;
	}

	@Override
	public Collection<InitializerContainer> getInitializersToLoadFirst() {
		return new ArrayList<InitializerContainer>();
	}

	public Collection<Symbol> getAbstractMethods() {
		return this.abstracts;
	}

}
