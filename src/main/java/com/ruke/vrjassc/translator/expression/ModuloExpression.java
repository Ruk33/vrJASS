package com.ruke.vrjassc.translator.expression;

public class ModuloExpression extends Expression {

    Expression a;
    Expression b;

    public ModuloExpression(Expression a, Expression b) {
        this.a = a;
        this.b = b;

        this.a.setParent(this);
        this.b.setParent(this);
    }

    @Override
    public String translate() {
        return "ModuloReal(" + this.a.translate() + "," + this.b.translate() + ")";
    }
}
