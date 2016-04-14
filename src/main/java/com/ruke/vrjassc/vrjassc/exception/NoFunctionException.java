package com.ruke.vrjassc.vrjassc.exception;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import org.antlr.v4.runtime.Token;

public class NoFunctionException extends CompileException {

	private Symbol symbol;

	public NoFunctionException(Token token, Symbol symbol) {
		super(token);
		this.symbol = symbol;
	}

	@Override
	public String getErrorMessage() {
		return "Element <" + symbol.getName() + "> is not a function";
	}

}
