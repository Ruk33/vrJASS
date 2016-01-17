package com.ruke.vrjassc.translator.expression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.MutualRecursion;

public class JassContainer extends StatementList {

	protected HashMap<Symbol, Statement> symbolStatement = new HashMap<Symbol, Statement>();
	protected Map<Symbol, MutualRecursion> recursion = new HashMap<Symbol, MutualRecursion>();
	protected StatementList globals = new StatementList();
		
	@Override
	public void add(Statement e) {
		this.symbolStatement.put(e.getSymbol(), e);
		super.add(e);
	}
	
	public void addGlobal(Statement e) {
		this.globals.add(e);
	}

	/*
	 * All credits to 
	 * 	http://www.electricmonk.nl/log/2008/08/07/dependency-resolving-algorithm/
	 * 	muZk
	 * Thanks!
	 */
	protected void doMagic(Statement statement, LinkedList<Statement> sorted, HashSet<Statement> seen) {
		if (statement == null) return;
		
		Symbol function = statement.getSymbol();
		Statement dstat;

		seen.add(statement);
		
		for (Symbol dependency : statement.getUsedFunctions()) {
			if (dependency == function) continue;
			
			dstat = this.symbolStatement.get(dependency);
			
			if (dstat == null) continue;
			
			if (!sorted.contains(dstat)) {
				if (seen.contains(dstat)) {
					this.createMutualRecursion(function);
					return;
				}

				this.doMagic(dstat, sorted, seen);
			}
		}
		
		if (!sorted.contains(statement)) sorted.add(statement);
	}
	
	
	protected LinkedList<Statement> sortFunctions() {
		LinkedList<Statement> sorted = new LinkedList<Statement>();
		HashSet<Statement> seen = new HashSet<Statement>();
		
		for (Statement statement : this.getStatements()) {
			this.doMagic(statement, sorted, seen);
		}
		
		return sorted;
	}
	
	protected void sort() {		
		this.statements = this.sortFunctions();
		
		GlobalStatement allGlobals = new GlobalStatement();
		
		for (MutualRecursion rec : this.recursion.values()) {
			allGlobals.add(rec.getGlobalVariableBlock());
			
			this.statements.addFirst(rec.getDummyDefinition());
			this.statements.addLast(rec.getDummyNoArgsDefinition());
		}
		
		allGlobals.add(this.globals);
		
		this.statements.addFirst(allGlobals);
	}
	
	protected void createMutualRecursion(Symbol function) {
		if (this.getMutualRecursion(function) == null) {
			MutualRecursion recursion = new MutualRecursion((FunctionSymbol) function);
			this.recursion.put(function, recursion);
		}
	}
	
	@Override
	public MutualRecursion getMutualRecursion(Symbol function) {
		return this.recursion.get(function);
	}

	@Override
	public String translate() {
		this.sort();
		return super.translate().trim();
	}
	
}
