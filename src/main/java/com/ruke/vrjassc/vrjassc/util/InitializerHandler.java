package com.ruke.vrjassc.vrjassc.util;

import java.util.HashMap;
import java.util.LinkedList;
import com.ruke.vrjassc.vrjassc.symbol.LibrarySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class InitializerHandler {
	
	private HashMap<Symbol, Integer> initializerOrder = new HashMap<Symbol, Integer>();
	private LinkedList<Symbol> initializers = new LinkedList<Symbol>();
	
	public void add(LibrarySymbol library) {
		if (this.initializerOrder.containsKey(library)) {
			return;
		}
		
		int order = this.initializers.size();
		
		for (Symbol requirement : library.getRequirements()) {
			this.add((LibrarySymbol) requirement);
			order = this.initializerOrder.get(requirement);
		}

		this.initializerOrder.put(library, order);
		this.initializers.add(library);
	}
	
	public LinkedList<Symbol> getInitializers() {
		return this.initializers;
	}
	
}
