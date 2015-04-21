package util;

import java.util.HashMap;
import java.util.Stack;

public class FunctionSorter {

	protected Stack<String> dummyFunctions;
	protected Stack<String> functions;
	protected HashMap<String, Integer> functionOrder;
		
	public FunctionSorter() {
		this.dummyFunctions = new Stack<String>();
		this.functions = new Stack<String>();
		this.functionOrder = new HashMap<String, Integer>();
	}

	public String getDummyPrefix() {
		return "vrjass_c_";
	}
	
	protected String getDummyBody(String name) {
		StringBuilder result = new StringBuilder();
		
		result.append("function ");
		result.append(this.getDummyPrefix());
		result.append(name);
		result.append(" takes nothing returns nothing");
		result.append("\n");
		result.append("call ExecuteFunc(\"");
		result.append(name);
		result.append("\")");
		result.append("\n");
		result.append("endfunction");
		
		return result.toString();
	}
	
	public void setFunctionBody(String name, String body) {
		int index = this.functionOrder.get(name);
		this.functions.add(index, body);
	}
	
	public void functionBeingDefined(String name) {
		if (!this.functionOrder.containsKey(name)) {
			this.functionOrder.put(name, this.functions.size());
		}
	}
	
	/**
	 * 
	 * @param whichFunction
	 * @param by
	 * @return false if the use of dummy was required
	 */
	public boolean functionBeingCalled(String whichFunction, String by) {
		if (whichFunction.equals(by)) {
			return true;
		}
		
		int order = this.functionOrder.get(by);
		boolean nicelySorted = true;

		if (this.functionOrder.containsKey(whichFunction)) {
			int whichFunctionOrder = this.functionOrder.get(whichFunction);
			
			if (order == whichFunctionOrder) {
				this.dummyFunctions.push(this.getDummyBody(whichFunction));
				nicelySorted = false;
			} else if (order > whichFunctionOrder) {
				order = whichFunctionOrder;
			}
		}
		
		this.functionOrder.put(whichFunction, order);
		
		return nicelySorted;
	}
	
	public Stack<String> getFunctions() {
		Stack<String> result = new Stack<String>();
		
		result.addAll(this.dummyFunctions);
		result.addAll(this.functions);
		
		return result;
	}
	
}
