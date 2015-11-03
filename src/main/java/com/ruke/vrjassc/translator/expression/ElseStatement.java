package com.ruke.vrjassc.translator.expression;

public class ElseStatement extends StatementBody {

	@Override
	public String translate() {
		return "else\n" + super.translate();
	}
	
}
