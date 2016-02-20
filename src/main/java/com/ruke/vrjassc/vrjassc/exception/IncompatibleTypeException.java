package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;

public class IncompatibleTypeException extends CompileException {

	private Symbol symbol;
	
	private Type given;
	
	public IncompatibleTypeException(Token token, Symbol symbol, Type given) {
		super(token);

		this.symbol = symbol;
		this.given = given;
	}

	@Override
	public String getErrorMessage() {
		if (this.given == null) {
			return "Element <" + this.symbol.getName() + "> must not return anything";
		}
		
		return String.format(
			"Element <%s> must have/return a value of type <%s> but given <%s>",
			this.symbol.getName(),
			this.symbol.getType().getName(),
			this.given.getName()
		);
	}

}
