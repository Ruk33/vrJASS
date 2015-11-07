package com.ruke.vrjassc.vrjassc.symbol;

import java.util.ArrayList;
import java.util.Collection;

import org.antlr.v4.runtime.Token;

public class ClassSymbol extends ScopeSymbol implements Type, InitializerContainer {

	private ClassSymbol _super;
	protected Symbol onInit;
	
	public ClassSymbol(String name, Scope scope, Token token) {
		super(name, scope, token);
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
	
	public Symbol resolveMember(ClassSymbol requesting, String name) {
		Symbol member = this.childs.getOrDefault(name, this.childsAliases.get(name));
		
		if (member == null || !requesting.hasAccessToMember(member)) {
			member = null;
			
			if (this.getSuper() != null) {
				return this.getSuper().resolveMember(requesting, name);
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
		return new ArrayList<InitializerContainer>();
	}

}
