package com.ruke.vrjassc.vrjassc.exception;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import org.antlr.v4.runtime.Token;

public class SuperException extends CompileException {

    private Symbol _class;

    public SuperException(Token token, Symbol _class) {
        super(token);
        this._class = _class;
    }

    @Override
    public String getErrorMessage() {
        return "Struct <" + this._class.getName() + "> can not use " +
        "super since it does not extends from anything";
    }

}
