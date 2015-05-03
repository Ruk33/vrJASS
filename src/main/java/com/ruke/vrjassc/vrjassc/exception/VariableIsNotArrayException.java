package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class VariableIsNotArrayException extends CompileException {

	protected String name;

	public VariableIsNotArrayException(Token token) {
		super(token);
		this.name = token.getText();
	}

	@Override
	public String getMessage() {
		return super.getMessage()
				+ String.format(" Variable <%s> is not an array", this.name);
	}

}
