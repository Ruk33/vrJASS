package com.ruke.vrjassc.translator.expression;

import java.util.LinkedList;
import java.util.List;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class StatementBody extends Statement {

	protected List<Statement> statements = new LinkedList<Statement>();
	
	public List<Statement> getList() {
		return this.statements;
	}
	
	public void append(Statement statement) {
		statement.setParent(this);
		this.statements.add(statement);
	}

	@Override
	public boolean usesFunction(Symbol function) {
		boolean result = super.usesFunction(function);
		
		for (Statement statement : this.statements) {
			if (statement.usesFunction(function)) {
				return true;
			}
		}
		
		return result;
	}
	
	@Override
	public String translate() {
		List<Statement> sortedStatements = new LinkedList<Statement>();
		String result = "";
		int index = 0;
		
		sortedStatements.addAll(this.statements);
		
		for (Statement statement : this.statements) {
			statement.sort(sortedStatements, index++);
		}
		
		for (Statement statement : sortedStatements) {
			result += statement.translate() + "\n";
		}
		
		return result;
	}
	
}
