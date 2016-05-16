package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class ImportException extends CompileException {

    private CompileException exception;

    public ImportException(Token token, CompileException exception) {
        super(token);
        this.exception = exception;
    }

    @Override
    public String getErrorMessage() {
        return this.exception.getErrorMessage();
    }
}
