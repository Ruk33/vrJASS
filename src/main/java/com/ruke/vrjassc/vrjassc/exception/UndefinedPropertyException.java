package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class UndefinedPropertyException extends CompileException {

	protected Symbol _class;
	protected String name;

	public UndefinedPropertyException(Token token, Symbol _class) {
		super(token);

		this._class = _class;
		this.name = token.getText();
	}

	@Override
	public String getMessage() {
		return super.getMessage()
				+ String.format(
						" Class <%s> does not have a property called <%s>",
						this._class.getName(), this.name);
	}

}
