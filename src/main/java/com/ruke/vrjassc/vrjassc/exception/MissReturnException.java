package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class MissReturnException extends CompileException {

	private Symbol function;

	public MissReturnException(Token token, Symbol function) {
		super(token);
		this.function = function;
	}

	@Override
	public String getErrorMessage() {
		return String.format(
			"Function <%s> is missing return of type <%s>",
			this.function.getName(),
			this.function.getType().getName()
		);
	}

}
