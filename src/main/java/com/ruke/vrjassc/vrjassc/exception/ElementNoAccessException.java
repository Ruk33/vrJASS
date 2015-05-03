package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class ElementNoAccessException extends CompileException {

	protected String name;

	public ElementNoAccessException(Token token) {
		super(token);
		this.name = token.getText();
	}

	@Override
	public String getMessage() {
		return super.getMessage()
				+ String.format(" No access to element <%s>", this.name);
	}

}
