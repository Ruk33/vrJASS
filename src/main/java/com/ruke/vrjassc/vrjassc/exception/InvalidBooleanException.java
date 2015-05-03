package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class InvalidBooleanException extends CompileException {

	protected String givenType;

	public InvalidBooleanException(Token token, String givenType) {
		super(token);
		this.givenType = givenType;
	}

	@Override
	public String getMessage() {
		return super.getMessage()
				+ String.format(
						" Expression type must be <boolean>, but <%s> type given",
						this.givenType);
	}

}
