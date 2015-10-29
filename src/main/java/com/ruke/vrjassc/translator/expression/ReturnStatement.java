package com.ruke.vrjassc.translator.expression;

public class ReturnStatement extends Statement {

	protected Expression value;
	
	public ReturnStatement(Expression value) {
		this.value = value;
		
		if (this.value != null) {
			this.value.setParent(this);
		}
	}
	
	@Override
	public String translate() {
		if (this.value != null) {
			return "return " + this.value.translate();
		}
		
		return "return";
	}
	
}
