package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class InvalidMathException extends CompileException {

	public InvalidMathException(Token token) {
		super(token);
	}

	@Override
	public String getErrorMessage() {
		return "Not a valid math operation";
	}

}
