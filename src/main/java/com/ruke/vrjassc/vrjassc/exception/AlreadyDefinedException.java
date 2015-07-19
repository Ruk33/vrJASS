package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class AlreadyDefinedException extends CompileException {

	private Symbol symbol;

	public AlreadyDefinedException(Token token, Symbol symbol) {
		super(token);
		this.symbol = symbol;
	}

	@Override
	public String getErrorMessage() {
		return String.format(
			"Element <%s> is already defined on %d:%d",
			this.symbol.getName(),
			this.symbol.getToken().getLine(),
			this.symbol.getToken().getCharPositionInLine()
		);
	}

}
