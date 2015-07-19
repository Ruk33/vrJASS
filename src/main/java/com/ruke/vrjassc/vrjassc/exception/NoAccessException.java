package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.symbol.Scope;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class NoAccessException extends CompileException {

	private Scope from;
	private Symbol to;

	public NoAccessException(Token token, Scope from, Symbol to) {
		super(token);

		this.from = from;
		this.to = to;
	}

	@Override
	public String getErrorMessage() {
		return String.format(
			"Element <%s> does not have access to element <%s>",
			this.from.getName(),
			this.to.getName()
		);
	}

}
