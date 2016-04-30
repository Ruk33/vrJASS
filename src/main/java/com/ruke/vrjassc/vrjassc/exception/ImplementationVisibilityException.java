package com.ruke.vrjassc.vrjassc.exception;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import org.antlr.v4.runtime.Token;

public class ImplementationVisibilityException extends CompileException {

    private Symbol method;
    private Symbol implementation;

    public ImplementationVisibilityException(Symbol method, Symbol implementation, Token token) {
        super(token);

        this.method = method;
        this.implementation = implementation;
    }

    @Override
    public String getErrorMessage() {
        return "Method <" + this.implementation.getName() + "> must be " + this.method.getVisibility();
    }
}
