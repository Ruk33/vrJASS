package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class TooFewArgumentsFunctionCallException extends CompileException {

	protected String name;

	public TooFewArgumentsFunctionCallException(Token token) {
		super(token);
		this.name = token.getText();
	}

	@Override
	public String getMessage() {
		return super.getMessage()
				+ String.format(" Too few arguments passed to function <%s>",
						this.name);
	}

}
