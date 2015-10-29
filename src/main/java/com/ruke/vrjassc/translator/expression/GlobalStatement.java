package com.ruke.vrjassc.translator.expression;

public class GlobalStatement extends StatementBody {

	@Override
	public String translate() {
		String globals = super.translate().trim();
		
		if (globals.isEmpty()) {
			return "";
		}
		
		return "globals\n" + globals + "\nendglobals";
	}
	
}
