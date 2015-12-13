package com.ruke.vrjassc.translator.expression;

public class WhileLoopStatement extends LoopStatement {
	
	public WhileLoopStatement(Expression condition) {
		Expression notCondition = new NotExpression(condition);
		Statement exit = new ExitWhenStatement(notCondition);
		
		this.add(exit);
	}

}
