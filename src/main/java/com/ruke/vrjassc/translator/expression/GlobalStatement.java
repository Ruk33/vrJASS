package com.ruke.vrjassc.translator.expression;

public class GlobalStatement extends StatementBody {

	@Override
	public String translate() {
		if (this.statements.isEmpty()) return "";
		return "globals\n" + super.translate().trim() + "\nendglobals";
	}
	
}
