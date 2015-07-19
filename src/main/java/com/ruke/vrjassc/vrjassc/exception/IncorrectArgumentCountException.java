package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class IncorrectArgumentCountException extends CompileException {

	private Symbol function;

	public IncorrectArgumentCountException(Token token, Symbol function) {
		super(token);
		this.function = function;
	}

	@Override
	public String getErrorMessage() {
		return "Incorrect amount of arguments passed to function <" + function.getName() + ">";
	}

}
