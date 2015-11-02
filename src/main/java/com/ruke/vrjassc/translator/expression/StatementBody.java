package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class StatementBody extends StatementList {

	protected StatementList declarations = new StatementList();
	
	public StatementList getDeclarations() {
		return this.declarations;
	}
	
	protected boolean canDeclareVariables() {
		if (this.getParent() instanceof FunctionDefinition == false) {
			return false;
		}
		
		if (this.getStatements().getLast() instanceof VariableStatement == false) {
			return false;
		}
		
		return true;
	}
	
	protected void addVariableStatement(VariableStatement e) {
		if (this.canDeclareVariables()) {
			super.add(e);
		} else {
			if (e.value == null) {
				this.getDeclarations().add(e);
			} else {
				Symbol s = e.getSymbol();
				Expression var = new VariableExpression(s, null);
				AssignmentStatement assign = new AssignmentStatement(var, e.value);
				
				this.getDeclarations().add(new VariableStatement(s, null));
				this.add(assign);
			}
		}
	}
	
	public void add(Statement e) {
		e.setParent(this);
		
		this.getUsedFunctions().addAll(e.getUsedFunctions());
		
		if (e instanceof VariableStatement) {
			this.addVariableStatement((VariableStatement) e);
		} else {
			if (e instanceof StatementBody) {
				this.getDeclarations().add(((StatementBody) e).getDeclarations());
			}
			
			super.add(e);
		}
	}
	
}
