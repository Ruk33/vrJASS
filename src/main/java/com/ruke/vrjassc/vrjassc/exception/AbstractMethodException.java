package com.ruke.vrjassc.vrjassc.exception;

import com.ruke.vrjassc.vrjassc.symbol.InterfaceSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import org.antlr.v4.runtime.Token;

public class AbstractMethodException extends CompileException {

	protected Symbol abstractContainer;
	protected Symbol _class;
	
	public AbstractMethodException(Symbol abstractContainer, Symbol _class, Token token) {
		super(token);
		this.abstractContainer = abstractContainer;
		this._class = _class;
	}

	@Override
	public String getErrorMessage() {
		String type = null;

		if (this.abstractContainer instanceof InterfaceSymbol) {
			type = "interface";
		} else {
			type = "abstract class";
		}

		return String.format(
			"Class %s must implement all methods of %s %s",
			this._class.getName(),
			type,
			this.abstractContainer.getName()
		);
	}

}
