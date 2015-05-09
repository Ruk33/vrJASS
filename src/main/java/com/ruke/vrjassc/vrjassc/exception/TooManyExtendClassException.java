package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

public class TooManyExtendClassException extends CompileException {

	protected String name;
	protected int extendCount;
	
	public TooManyExtendClassException(Token token, String name, int extendCount) {
		super(token);
		this.name = name;
		this.extendCount = extendCount;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				String.format(
					" Classes can only extend from one class. <%s> extends from %d",
					this.name,
					this.extendCount
				);
	}

}
