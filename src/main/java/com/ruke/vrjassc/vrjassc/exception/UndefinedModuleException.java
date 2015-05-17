package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class UndefinedModuleException extends CompileException {

	protected String name;
	
	public UndefinedModuleException(Token token, String name) {
		super(token);
		this.name = name;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + String.format(" Module <%s> is not defined", this.name);
	}

}
