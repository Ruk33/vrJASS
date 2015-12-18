package com.ruke.vrjassc.vrjassc.exception;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class InterfaceMethodException extends CompileException {

	protected Symbol _interface;
	protected Symbol _class;
	
	public InterfaceMethodException(Symbol _interface, Symbol _class, Token token) {
		super(token);
		this._interface = _interface;
		this._class = _class;
	}

	@Override
	public String getErrorMessage() {
		return String.format(
			"Class %s must implement all methods of interface %s",
			this._class.getName(),
			this._interface.getName()
		);
	}

}
