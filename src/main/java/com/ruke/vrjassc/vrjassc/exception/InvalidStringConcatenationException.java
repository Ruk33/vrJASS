package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class InvalidStringConcatenationException extends CompileException {

	public InvalidStringConcatenationException(Token token) {
		super(token);
	}

	@Override
	public String getErrorMessage() {
		return "Invalid string concatenation";
	}

}
