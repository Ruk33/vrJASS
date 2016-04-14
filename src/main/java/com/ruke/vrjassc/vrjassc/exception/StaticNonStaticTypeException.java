package com.ruke.vrjassc.vrjassc.exception;

import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import org.antlr.v4.runtime.Token;

public class StaticNonStaticTypeException extends CompileException {

	private Symbol member;
	
	public StaticNonStaticTypeException(Token token, Symbol member) {
		super(token);
		this.member = member;
	}

	@Override
	public String getErrorMessage() {
		if (this.member.hasModifier(Modifier.STATIC)) {
			return String.format("Element <%s> is static", this.member.getName());
		} else {
			return String.format("Element <%s> is not static", this.member.getName());
		}
	}

}
