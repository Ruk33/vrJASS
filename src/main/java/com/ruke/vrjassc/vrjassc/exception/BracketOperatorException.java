package com.ruke.vrjassc.vrjassc.exception;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import org.antlr.v4.runtime.Token;

public class BracketOperatorException extends CompileException {

    private Symbol operator;

    public BracketOperatorException(Token token, Symbol operator) {
        super(token);
        this.operator = operator;
    }

    @Override
    public String getErrorMessage() {
        if ("[]=".equals(this.operator.getName())) {
            return "Operator []= must take two parameters, being the first of type integer";
        }

        return "Operator [] must take one parameter of type integer and return a value";
    }
}
