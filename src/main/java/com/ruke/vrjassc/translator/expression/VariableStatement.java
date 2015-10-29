package com.ruke.vrjassc.translator.expression;

import java.util.List;

import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class VariableStatement extends Statement {

	protected Symbol variable;
	protected Expression value;
	
	public VariableStatement(Symbol variable, Expression value) {
		this.variable = variable;
		this.value = value;
		
		if (this.value != null) {
			this.value.setParent(this);
		}
	}
	
	@Override
	public Symbol getSymbol() {
		return this.variable;
	}
	
	@Override
	public String translate() {
		String type = this.variable.getType().getName();
		String name = this.variable.getName();
		
		String result = type + " " + name;
		
		if (this.variable instanceof LocalVariableSymbol) {
			result = "local " + result;
		}
		
		if (this.value != null) {
			result += "=" + this.value.translate();
		}
		
		return result;
	}

	@Override
	public void sort(List<Statement> list, int index) {
		Statement prev = list.get(Integer.max(0, index-1));
		
		if (prev instanceof VariableStatement == false) {
			list.remove(index);
			
			if (this.value == null) {
				list.add(0, this);
			} else {
				Statement varDeclaration = new VariableStatement(this.variable, null);
				
				Expression assignExpr = new VariableExpression(this.variable, null);
				Statement varAssign = new AssignmentStatement(assignExpr, this.value);
				
				list.add(0, varDeclaration);
				list.add(index+1, varAssign);
			}
		}
	}
	
}
