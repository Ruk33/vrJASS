package util;

import java.util.HashMap;
import java.util.Stack;

public class FunctionSorter {

	protected Stack<String> functions;
	protected HashMap<String, Integer> functionOrder;
		
	public FunctionSorter() {
		this.functions = new Stack<String>();
		this.functionOrder = new HashMap<String, Integer>();
	}
	
	public void functionBeingDefined(String name, String value) {
		int order;
		
		if (this.functionOrder.containsKey(name)) {
			order = this.functionOrder.get(name);
			this.functions.add(order, value);
		} else {
			order = this.functions.size();
			this.functions.push(value);
			this.functionOrder.put(name, order);
		}
	}
	
	public void functionBeingCalled(String whichFunction, String by) {
		int order = this.functionOrder.getOrDefault(by, 0);
		
		if (this.functionOrder.containsKey(whichFunction)) {
			int whichFunctionOrder = this.functionOrder.get(whichFunction);
			
			if (order > whichFunctionOrder) {
				order = whichFunctionOrder;
			}
		}
		
		this.functionOrder.put(whichFunction, order);
	}
	
	public Stack<String> getFunctions() {
		return this.functions;
	}
	
}
