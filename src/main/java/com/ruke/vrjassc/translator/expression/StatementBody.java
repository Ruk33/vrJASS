package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class StatementBody extends StatementList {

	/**
	 * Variable declarations that are not in the header of the body
	 */
	protected StatementList declarations = new StatementList();
	
	public StatementList getDeclarations() {
		return this.declarations;
	}
	
	protected boolean canDeclareVariables() {
		if (this.getParent() instanceof FunctionDefinition == false) {
			return false;
		}
		
		if (!this.getStatements().isEmpty()) {
			if (this.getStatements().getLast() instanceof VariableStatement == false) {
				return false;
			}
		}
		
		return true;
	}
	
	protected void addVariableStatement(VariableStatement e) {
		if (this.canDeclareVariables()) {
			super.add(e);
		} else {
			if (e.value == null || e.value instanceof DefaultValue) {
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

	public LoopStatement getLoop() {
		Expression loop = this;

		while (loop instanceof LoopStatement == false) {
			if (loop == null) {
				break;
			}

			loop = loop.getParent();
		}

		if (loop instanceof LoopStatement) {
			return (LoopStatement) loop;
		}

		return null;
	}
	
	public void add(Statement e) {
		if (e == null) {
			return;
		}
		
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
