package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;

public class IncorrectReturnTypeFunctionException extends CompileException {

	protected FunctionSymbol function;
	protected String givenType;

	public IncorrectReturnTypeFunctionException(Token token,
			FunctionSymbol function, String givenType) {
		super(token);

		this.function = function;
		this.givenType = givenType;
	}

	@Override
	public String getMessage() {
		return super.getMessage()
				+ String.format(
						" Function <%s> must return <%s> but it is trying to return <%s>",
						this.function.getName(), this.function.getType(),
						this.givenType);
	}

}
