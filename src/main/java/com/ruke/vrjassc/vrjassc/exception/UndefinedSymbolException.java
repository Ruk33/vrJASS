package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class UndefinedSymbolException extends CompileException {

	private String name;
	
	/**
	 * 
	 * @param token
	 * @param name
	 * @param type Variable, function, etc.
	 */
	public UndefinedSymbolException(Token token, String name) {
		super(token);
		this.name = name;
	}

	@Override
	public String getErrorMessage() {
		return String.format("Element <%s> is not defined", this.name);
	}

}
