package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class StaticTypeException extends CompileException {

	public StaticTypeException(Token token) {
		super(token);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
