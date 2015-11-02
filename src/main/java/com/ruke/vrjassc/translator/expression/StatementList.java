package com.ruke.vrjassc.translator.expression;

import java.util.LinkedList;
import java.util.Stack;

public class StatementList extends Statement {

	protected LinkedList<Statement> statements = new LinkedList<Statement>();
	
	public LinkedList<Statement> getStatements() {
		return this.statements;
	}
	
	public void add(Statement e) {
		this.statements.add(e);
	}
	
	@Override
	public String translate() {
		Stack<String> result = new Stack<String>();
		String translated;
		
		for (Statement statement : this.statements) {
			translated = statement.translate();
			
			if (!translated.isEmpty()) {
				result.push(translated);
			}
		}
		
		if (result.isEmpty()) {
			return "";
		}
		
		return String.join("\n", result).trim().concat("\n");
	}

}
