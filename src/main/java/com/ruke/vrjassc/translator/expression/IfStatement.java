package com.ruke.vrjassc.translator.expression;

public class IfStatement extends StatementBody {

	protected Expression condition;
	
	public IfStatement(Expression condition) {
		this.condition = condition;
		this.condition.setParent(this);
	}
	
	@Override
	public String translate() {
		return String.format(
			"if %s then\n"
				+ "%s"
			+ "endif",
			this.condition.translate(),
			super.translate()
		);
	}

}
