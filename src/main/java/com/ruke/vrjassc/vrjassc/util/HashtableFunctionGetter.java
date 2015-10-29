package com.ruke.vrjassc.vrjassc.util;

import java.util.Hashtable;

import com.ruke.vrjassc.vrjassc.symbol.Type;

public class HashtableFunctionGetter {

	protected Hashtable<String, String> save;
	protected Hashtable<String, String> load;
	
	public HashtableFunctionGetter() {
		this.save = new Hashtable<String, String>();
		this.load = new Hashtable<String, String>();
		
		this.save.put("integer", "SaveInteger");
		this.load.put("integer", "LoadInteger");
	}
	
	public String getLoadFunction(Type type) {
		return this.load.getOrDefault(type.getName(), "LoadInteger");
	}
	
	public String getSaveFunction(Type type) {
		return this.save.getOrDefault(type.getName(), "SaveInteger");
	}
	
}
