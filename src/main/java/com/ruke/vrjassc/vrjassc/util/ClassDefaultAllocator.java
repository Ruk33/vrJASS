package com.ruke.vrjassc.vrjassc.util;

import java.util.Stack;

import com.ruke.vrjassc.vrjassc.symbol.MethodSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.VariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Visibility;

public class ClassDefaultAllocator {

	protected Symbol symbol;
	protected String name;

	protected String getGlobalVariableName() {
		return "struct_" + this.name + "_s__recycle";
	}
	
	public String getSetPropertiesName() {
		return "struct_" + this.name + "_setProperties";
	}

	public String getAllocatorName() {
		return "struct_s_" + this.name + "_allocate";
	}
	
	public String getSetProperties() {
		String result = "function " + this.getSetPropertiesName() +
				" takes integer this returns nothing" + System.lineSeparator();
		
		for (Symbol property : ((ClassSymbol) this.symbol).getProperties()) {
			if (((VariableSymbol) property).getValue() != null) {
				result += "set " + property.getFullName() + "[this]=" + ((VariableSymbol) property).getValue() + System.lineSeparator();
			}
		}
		
		result += "endfunction";
		
		return result;
	}

	public String getAllocator() {
		StringBuilder result = new StringBuilder();

		result.append("function ");
		result.append(this.getAllocatorName());
		result.append(" takes nothing returns integer");
		result.append(System.lineSeparator());
		result.append("local integer instance=");
		result.append(this.getGlobalVariableName());
		result.append("[0]");
		result.append(System.lineSeparator());
		result.append("if (");
		result.append(this.getGlobalVariableName());
		result.append("[0]==0) then");
		result.append(System.lineSeparator());
		result.append("set ");
		result.append(this.getGlobalVariableName());
		result.append("[0]=instance+1");
		result.append(System.lineSeparator());
		result.append("else");
		result.append(System.lineSeparator());
		result.append("set ");
		result.append(this.getGlobalVariableName());
		result.append("[0]=");
		result.append(this.getGlobalVariableName());
		result.append("[instance]");
		result.append(System.lineSeparator());
		result.append("endif");
		result.append(System.lineSeparator());
		result.append("call ");
		result.append(this.getSetPropertiesName());
		result.append("(instance)");
		result.append(System.lineSeparator());
		result.append("return instance");
		result.append(System.lineSeparator());
		result.append("endfunction");

		return result.toString();
	}

	public String getDeallocatorName() {
		return "struct_" + this.name + "_deallocate";
	}

	public String getDeallocator() {
		StringBuilder result = new StringBuilder();

		result.append("function ");
		result.append(this.getDeallocatorName());
		result.append(" takes integer this returns nothing");
		result.append(System.lineSeparator());
		result.append("set ");
		result.append(this.getGlobalVariableName());
		result.append("[this]=");
		result.append(this.getGlobalVariableName());
		result.append("[0]");
		result.append(System.lineSeparator());
		result.append("set ");
		result.append(this.getGlobalVariableName());
		result.append("[0]=this");
		result.append(System.lineSeparator());
		result.append("endfunction");

		return result.toString();
	}

	public ClassDefaultAllocator(Symbol symbol) {
		this.symbol = symbol;
		this.name = symbol.getName();
		
		new MethodSymbol("allocate", this.symbol.getType(), true, false, Visibility.PRIVATE, this.symbol, null);
		new MethodSymbol("deallocate", this.symbol.getType(), false, false, Visibility.PRIVATE, this.symbol, null);
	}

	public Stack<String> getGlobals() {
		Stack<String> globals = new Stack<String>();
		globals.push("integer array " + this.getGlobalVariableName());

		return globals;
	}

}
