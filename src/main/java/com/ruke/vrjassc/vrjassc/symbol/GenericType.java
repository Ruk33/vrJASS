package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class GenericType extends Symbol implements Type {

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
        if (this.getType() == this) {
            return this == symbol.getType();
        }

        return this.getType().isTypeCompatible(symbol);
    }

}
