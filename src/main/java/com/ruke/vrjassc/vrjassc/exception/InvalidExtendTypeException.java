package com.ruke.vrjassc.vrjassc.exception;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import org.antlr.v4.runtime.Token;

public class InvalidExtendTypeException extends CompileException {

	private Symbol _extends;

	public InvalidExtendTypeException(Token token, Symbol _extends) {
		super(token);
		this._extends = _extends;
	}

	@Override
	public String getErrorMessage() {
		return "Element <" + this._extends.getName() + "> is not a valid extendable class";
	}

}
