package com.ruke.vrjassc.vrjassc.util;

import java.util.HashMap;
import java.util.LinkedList;

import com.ruke.vrjassc.vrjassc.symbol.InitializerContainerSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class InitializerHandler {
	
	private HashMap<Symbol, Integer> initializerOrder = new HashMap<Symbol, Integer>();
	private LinkedList<Symbol> initializers = new LinkedList<Symbol>();
	
	public void add(Symbol symbol) {
		if (symbol instanceof InitializerContainerSymbol == false) {
			return;
		}
		
		InitializerContainerSymbol initializerContainer =
				(InitializerContainerSymbol) symbol;
		
		if (this.initializerOrder.containsKey(initializerContainer)) {
			return;
		}
		
		int order = this.initializers.size();
		
		for (Symbol loadFirst : initializerContainer.getSymbolsToLoadFirst()) {
			this.add(loadFirst);
			order = this.initializerOrder.get(loadFirst);
		}

		this.initializerOrder.put(initializerContainer, order);
		this.initializers.add(initializerContainer);
	}
	
	public LinkedList<Symbol> getInitializers() {
		LinkedList<Symbol> result = new LinkedList<Symbol>();
		
		for (Symbol symbol : this.initializers) {
			if (((InitializerContainerSymbol) symbol).getInitializer() != null) {
				result.add(symbol);
			}
		}
		
		return result;
	}
	
}
