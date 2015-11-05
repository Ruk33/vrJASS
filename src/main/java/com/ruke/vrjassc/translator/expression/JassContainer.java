package com.ruke.vrjassc.translator.expression;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.MutualRecursion;

public class JassContainer extends StatementList {

	protected Map<Symbol, Integer> pos = new HashMap<Symbol, Integer>();
	protected Map<Symbol, MutualRecursion> recursion = new HashMap<Symbol, MutualRecursion>();
	protected StatementList globals = new StatementList();
	
	@Override
	public void add(Statement e) {
		e.setParent(this);
		
		this.pos.put(e.getSymbol(), this.statements.size());
		super.add(e);
	}
	
	public void addGlobal(Statement e) {
		this.globals.add(e);
	}
		
	protected void sort() {
		LinkedList<Statement> sorted = new LinkedList<Statement>();
		FunctionDefinition def;
		Statement funDef;
		int index;
		
		sorted.addAll(this.getStatements());

		for (Statement statement : this.statements) {
			if (statement instanceof FunctionDefinition == false) {
				continue;
			}
			
			def = (FunctionDefinition) statement;
			index = this.pos.get(def.getSymbol());
			
			for (Symbol fun : def.getUsedFunctions()) {
				if (index < this.pos.get(fun)) {
					funDef = this.statements.get(this.pos.get(fun));
					
					if (def.hasMutualRecursionWith(funDef)) {
						this.createMutualRecursion(fun);
					} else {
						index = this.pos.get(fun);
					}
				}
			}
			
			if (index != this.pos.get(def.getSymbol())) {
				sorted.remove(def);
				sorted.add(index, def);
				
				this.pos.put(def.getSymbol(), index);
			}
		}
		
		GlobalStatement allGlobals = new GlobalStatement();
		
		for (MutualRecursion rec : this.recursion.values()) {
			allGlobals.add(rec.getGlobalVariableBlock());
			
			sorted.addFirst(rec.getDummyDefinition());
			sorted.addLast(rec.getDummyNoArgsDefinition());
		}
		
		allGlobals.add(this.globals);
		
		sorted.addFirst(allGlobals);
		
		this.statements = sorted;
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
