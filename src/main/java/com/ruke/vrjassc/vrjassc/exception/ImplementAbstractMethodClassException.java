package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class ImplementAbstractMethodClassException extends CompileException {

	protected String name;
	
	public ImplementAbstractMethodClassException(Token token, String name) {
		super(token);
		
		this.name = name;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				String.format(
					" Class <%s> must implements all abstract methods",
					this.name
				);
	}

}
