package com.ruke.vrjassc.translator.expression;

public class GlobalStatement extends StatementList {

	@Override
	public String translate() {
		return "globals\n" + super.translate() + "endglobals";
	}
	
}
