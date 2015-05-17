package com.ruke.vrjassc.vrjassc.util;

import java.util.HashMap;
import java.util.Stack;

import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.NativeFunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class FunctionSorter {

	protected Stack<String> dummyGlobals;
	protected Stack<String> dummyFunctions;
	protected Stack<String> dummyNoArgsFunctions;
	protected Stack<String> functions;
	protected HashMap<String, Integer> functionOrder;

	public FunctionSorter() {
		this.dummyGlobals = new Stack<String>();
		this.dummyFunctions = new Stack<String>();
		this.dummyNoArgsFunctions = new Stack<String>();
		this.functions = new Stack<String>();
		this.functionOrder = new HashMap<String, Integer>();
	}

	public String getDummyPrefix() {
		return "vrjass_c_";
	}

	public String getDummyNoArgsPrefix() {
		return this.getDummyPrefix() + "noargs_";
	}

	protected String getDummyFunctionBody(FunctionSymbol function) {
		StringBuilder result = new StringBuilder();
		String dummyVariable;

		result.append("function ");
		result.append(this.getDummyPrefix());
		result.append(function.getName());
		result.append(" takes ");

		if (function.getParams().size() == 0) {
			result.append("nothing");
		} else {
			for (Symbol param : function.getParams()) {
				result.append(param.getType());
				result.append(" ");
				result.append(param.getName());
				result.append(",");
			}

			// remove last ","
			result.deleteCharAt(result.length() - 1);
		}

		result.append(" returns ");
		result.append(function.getType());
		result.append(System.lineSeparator());

		if (function.getParams().size() > 0) {
			for (Symbol param : function.getParams()) {
				dummyVariable = this.getDummyPrefix() + function.getName()
						+ "_" + param.getName();

				this.dummyGlobals.push(param.getType() + " " + dummyVariable);

				result.append("set ");
				result.append(dummyVariable);
				result.append("=");
				result.append(param.getName());
				result.append(System.lineSeparator());
			}
		}

		result.append("call ExecuteFunc(\"");
		result.append(this.getDummyNoArgsPrefix());
		result.append(function.getName());
		result.append("\")");
		result.append(System.lineSeparator());

		if (!function.getType().equals("nothing")) {
			dummyVariable = this.getDummyPrefix() + function.getName()
					+ "_return";

			this.dummyGlobals.push(function.getType() + " " + dummyVariable);

			result.append("return ");
			result.append(dummyVariable);
			result.append(System.lineSeparator());
		}

		result.append("endfunction");

		return result.toString();
	}

	protected String getDummyFunctionNoArgsBody(FunctionSymbol function) {
		StringBuilder result = new StringBuilder();

		result.append("function ");
		result.append(this.getDummyNoArgsPrefix());
		result.append(function.getName());
		result.append(" takes nothing returns nothing");
		result.append(System.lineSeparator());

		if (function.getType().equals("nothing")) {
			result.append("call ");
		} else {
			result.append("set ");
			result.append(this.getDummyPrefix());
			result.append(function.getName());
			result.append("_return=");
		}

		result.append(function.getName());
		result.append("(");

		if (function.getParams().size() > 0) {
			for (Symbol param : function.getParams()) {
				result.append(this.getDummyPrefix());
				result.append(function.getName());
				result.append("_");
				result.append(param.getName());
				result.append(",");
			}

			result.deleteCharAt(result.length() - 1);
		}

		result.append(")");
		result.append(System.lineSeparator());
		result.append("endfunction");

		return result.toString();
	}

	protected void createDummyFunction(FunctionSymbol function) {
		this.dummyFunctions.push(this.getDummyFunctionBody(function));
		this.dummyNoArgsFunctions.push(this
				.getDummyFunctionNoArgsBody(function));
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
	public boolean functionBeingCalled(FunctionSymbol whichFunction, String by) {
		String whichFunctionName = whichFunction.getFullName();

		if (whichFunctionName.equals(by)) {
			return true;
		}

		if (whichFunction instanceof NativeFunctionSymbol) {
			return true;
		}

		boolean nicelySorted = true;

		int order = this.functionOrder.getOrDefault(by, 0);

		if (this.functionOrder.containsKey(whichFunctionName)) {
			int whichFunctionOrder = this.functionOrder.get(whichFunctionName);

			if (order == whichFunctionOrder) {
				this.createDummyFunction(whichFunction);
				nicelySorted = false;
			} else if (order > whichFunctionOrder) {
				order = whichFunctionOrder;
			}
		}

		this.functionOrder.put(whichFunctionName, order);

		return nicelySorted;
	}

	public Stack<String> getDummyGlobals() {
		return this.dummyGlobals;
	}

	public Stack<String> getFunctions() {
		Stack<String> result = new Stack<String>();

		result.addAll(this.dummyFunctions);
		result.addAll(this.functions);
		result.addAll(this.dummyNoArgsFunctions);

		return result;
	}

}
