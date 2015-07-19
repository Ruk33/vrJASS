package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class InvalidImplementTypeException extends CompileException {

	private Symbol symbol;

	public InvalidImplementTypeException(Token token, Symbol symbol) {
		super(token);
		this.symbol = symbol;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return "Element <" + this.symbol.getName() + "> is not a valid implementable interface";
	}

}
