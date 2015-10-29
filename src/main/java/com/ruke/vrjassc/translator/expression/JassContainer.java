package com.ruke.vrjassc.translator.expression;

import java.util.HashMap;
import java.util.Stack;

import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.MutualRecursion;

public class JassContainer extends StatementBody {

	protected HashMap<Symbol, MutualRecursion> mutualRecursionMap;
	protected StatementBody globals;
	
	@Override
	public JassContainer getJassContainer() {
		return this;
	}
	
	public JassContainer() {
		this.mutualRecursionMap = new HashMap<Symbol, MutualRecursion>();
		this.globals = new GlobalStatement();
	}
	
	public void registerMutualRecursion(FunctionSymbol function) {
		this.mutualRecursionMap.put(function, new MutualRecursion(function));
	}
	
	public MutualRecursion getMutualRecursion(FunctionSymbol function) {
		return this.mutualRecursionMap.get(function);
	}
	
	public StatementBody getGlobals() {
		return this.globals;
	}
	
	public StatementBody getAllGlobals() {
		StatementBody allGlobals = new GlobalStatement();
		
		allGlobals.append(this.globals);
		
		for (MutualRecursion recursion : this.mutualRecursionMap.values()) {
			allGlobals.append(recursion.getGlobalVariableBlock());
		}
		
		return allGlobals;
	}
	
	protected String translateMutualRecursionFuncs(boolean noArgs) {
		String result = "";
		
		for (MutualRecursion recursion : this.mutualRecursionMap.values()) {
			if (noArgs) {
				result += recursion.getDummyNoArgsDefinition().translate();
			} else {
				result += recursion.getDummyDefinition().translate();
			}
			
			result += "\n";
		}
		
		return result.trim();
	}
	
	@Override
	public String translate() {
		Stack<String> result = new Stack<String>();
		String body = super.translate();
		
		result.push(this.getAllGlobals().translate());
		result.push(this.translateMutualRecursionFuncs(false));
		result.push(body.trim());
		result.push(this.translateMutualRecursionFuncs(true));
		
		return String.join("\n", result).trim();
	}
	
}
