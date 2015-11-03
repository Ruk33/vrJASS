package com.ruke.vrjassc.translator.expression;

public class ExitWhenStatement extends Statement {

	protected Expression condition;
	
	public ExitWhenStatement(Expression condition) {
		this.condition = condition;
	}
	
	@Override
	public String translate() {
		return "exitwhen " + this.condition.translate();
	}

}
