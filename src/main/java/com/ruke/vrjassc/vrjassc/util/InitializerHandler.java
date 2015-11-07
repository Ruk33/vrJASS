package com.ruke.vrjassc.vrjassc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruke.vrjassc.vrjassc.symbol.InitializerContainer;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class InitializerHandler {
	
	private Map<InitializerContainer, Integer> initializerOrder;
	private List<InitializerContainer> initializers;
	
	public InitializerHandler() {
		this.initializerOrder = new HashMap<InitializerContainer, Integer>();
		this.initializers = new ArrayList<InitializerContainer>();
	}
	
	public void add(InitializerContainer init) {
		if (this.initializerOrder.containsKey(init)) {
			return;
		}
		
		int order = this.initializers.size();
		
		for (InitializerContainer loadFirst : init.getInitializersToLoadFirst()) {
			this.add(loadFirst);
			order = this.initializerOrder.get(loadFirst);
		}

		this.initializerOrder.put(init, order);
		this.initializers.add(init);
	}
	
	public List<Symbol> getInitializers() {
		List<Symbol> result = new ArrayList<Symbol>();
		
		for (InitializerContainer init : this.initializers) {
			if (init.getInitializer() != null) {
				result.add(init.getInitializer());
			}
		}
		
		return result;
	}
	
}
