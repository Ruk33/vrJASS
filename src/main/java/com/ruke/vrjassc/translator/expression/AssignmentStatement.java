package com.ruke.vrjassc.translator.expression;

public class AssignmentStatement extends Statement {

	protected Expression name;
	protected Expression value;
	
	public AssignmentStatement(Expression name, Expression value) {
		this.name = name;
		this.value = value;
		
		this.name.setParent(this);
		this.value.setParent(this);
	}
	
	@Override
	public String translate() {
		if (this.name instanceof ChainExpression) {
			((ChainExpression) this.name).setValue(this.value);
			return "call " + this.name.translate();
		}
		
		return "set " + this.name.translate() + "=" + this.value.translate();
	}

}
