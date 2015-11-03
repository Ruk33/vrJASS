package com.ruke.vrjassc.translator.expression;

public class ElseIfStatement extends StatementBody {
	
	protected Expression condition;
	
	public ElseIfStatement(Expression condition) {
		this.condition = condition;
	}
	
	@Override
	public String translate() {
		return "elseif " + this.condition.translate() + " then\n" + super.translate();
	}
	
}
