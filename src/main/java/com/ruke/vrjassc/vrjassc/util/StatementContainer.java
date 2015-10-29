package com.ruke.vrjassc.vrjassc.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.ruke.vrjassc.translator.expression.FunctionDefinition;
import com.ruke.vrjassc.translator.expression.Statement;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

/**
 * @deprecated
 * @author Ruke
 *
 */
public class StatementContainer {
	
	protected LinkedList<Statement> statements = new LinkedList<Statement>();
	protected Map<Symbol, Integer> order = new HashMap<Symbol, Integer>();
	protected int counter;
	
	public void add(Statement statement) {
		this.statements.add(statement);
		
		if (statement instanceof FunctionDefinition) {
			this.order.put(statement.getSymbol(), ++this.counter);
		}
	}
	
	public void sort() {
		Symbol symbol;
		
		for (Statement statement : this.statements) {
			symbol = statement.getSymbol();
			
			if (symbol == null) {
				continue;
			}
			
			if (this.order.containsKey(symbol)) {
				System.out.println(symbol.getName() + " " + (statement.order - this.order.get(symbol)));
			}
		}
		
		Object[] sorted = this.statements.toArray();
		Arrays.sort(sorted);

		this.statements.clear();
		
		for (Object statement : sorted) {
			this.statements.add((Statement) statement);
		}
	}
	
	public LinkedList<Statement> getAll() {
		return this.statements;
	}

}
