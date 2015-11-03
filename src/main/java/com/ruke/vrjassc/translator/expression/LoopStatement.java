package com.ruke.vrjassc.translator.expression;

public class LoopStatement extends StatementBody {

	@Override
	public String translate() {
		return "loop\n"+ super.translate() + "endloop";
	}
	
}
