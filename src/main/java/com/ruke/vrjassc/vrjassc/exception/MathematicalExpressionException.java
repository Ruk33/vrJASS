package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class MathematicalExpressionException extends CompileException {

	public MathematicalExpressionException(Token token) {
		super(token);
	}

	@Override
	public String getMessage() {
		return super.getMessage()
				+ " Incorrect mathematical expression (only integers and reals)";
	}

}
