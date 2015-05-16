package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class UnunitializedVariableException extends CompileException {

	protected String name;
	
	public UnunitializedVariableException(Token token, String name) {
		super(token);
		this.name = name;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + String.format(" Variable <%s> is not initialized", this.name);
	}

}
