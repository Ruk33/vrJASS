package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class InvalidTypeException extends CompileException {

	private Symbol symbol;

	public InvalidTypeException(Token token, Symbol symbol) {
		super(token);
		this.symbol = symbol;
	}

	@Override
	public String getErrorMessage() {
		return String.format("<%s> is not a valid type", this.symbol.getName());
	}

}
