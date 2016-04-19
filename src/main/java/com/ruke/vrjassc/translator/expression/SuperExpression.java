package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class SuperExpression extends Expression {

    protected ClassSymbol _super;

    public SuperExpression(ClassSymbol _super) {
        this._super = _super;
    }

    @Override
    public Symbol getSymbol() {
        return this._super;
    }

    @Override
    public String translate() {
        return "this";
    }

}
