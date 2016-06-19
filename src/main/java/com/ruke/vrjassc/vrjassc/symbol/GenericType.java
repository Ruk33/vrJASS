package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class GenericType extends ScopeSymbol implements Type {

    public GenericType(String name, Scope scope, Token token) {
        super(name, scope, token);
        this.setType(this);
    }

    @Override
    public String getName() {
        if (this.getType() == this) {
            return super.getName();
        }

        return this.getType().getName();
    }

    public boolean isTypeCompatible(Symbol symbol) {
        if (symbol.getType() instanceof GenericType && this.getGeneric() != null) {
            return this.getGeneric().isTypeCompatible(((Symbol) symbol.getType()).getGeneric());
        }

        if (((Symbol)this.getType()).getGeneric() != null) {
            return ((Symbol)this.getType()).getGeneric().isTypeCompatible(symbol);
        }

        return this == symbol.getType();
    }

    @Override
    public boolean isUserType() {
        if (this.getType() == this) {
            return false;
        }

        return this.getType().isUserType();
    }

    @Override
    public Symbol resolve(Scope requesting, String name) {
        if (this.getType() instanceof ScopeSymbol) {
            return ((ScopeSymbol) this.getType()).resolve(requesting, name);
        }

        return null;
    }
}
