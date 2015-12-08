package com.ruke.vrjassc.vrjassc.symbol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.Token;

public class ClassSymbol extends ScopeSymbol implements Type, InitializerContainer {

	private ClassSymbol _super;
	protected Set<ModuleSymbol> modules;
	protected Symbol onInit;
	
	public ClassSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
		this.setModifier(Modifier.MEMBER, true);
		this.modules = new HashSet<ModuleSymbol>();
	}

	@Override
	public Type getType() {
		return this;
	}
	
	@Override
	public Symbol define(Symbol symbol) {
		if (symbol.getName().equals("onInit")) {
			this.setInitializer(symbol);
		}
		
		if (symbol instanceof ModuleSymbol) {
			this.modules.add((ModuleSymbol) symbol);
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
		} else {
			return this.hasAccess(member);
		}
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
		
		if (member == null || !requesting.hasAccessToMember(member)) {
			member = null;
			
			if (this.getSuper() != null) {
				member = this.getSuper().resolveMember(requesting, name);
			}
		}
		
		if (member == null) {
			for (ModuleSymbol module : this.modules) {
				member = module.resolve(this, name);
				if (member != null) {
					break;
				}
			}
		}
		
		return member;
	}
	
	public ClassSymbol getSuper() {
		return this._super;
	}
	
	public ClassSymbol extendsFrom(ClassSymbol _super) {
		this._super = _super;
		return this;
	}
	
	@Override
	public boolean isTypeCompatible(Symbol symbol) {
		Type sType = symbol.getType();
		
		if (!(sType instanceof ClassSymbol || sType instanceof InterfaceSymbol)) {
			return false;
		}
		
		if (this.getName().equals(sType.getName())) {
			return true;
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
		return new ArrayList<InitializerContainer>(this.modules);
	}

}
