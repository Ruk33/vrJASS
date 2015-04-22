package util;

import java.util.Stack;

public class ClassDefaultAllocator {

	protected String name;
	
	protected String getGlobalVariableName() {
		return "struct_" + this.name + "_s__recycle";
	}
	
	public String getAllocatorName() {
		return "struct_s_" + this.name + "_allocate";
	}
	
	public String getAllocator() {
		StringBuilder result = new StringBuilder();
		
		result.append("function ");
		result.append(this.getAllocatorName());
		result.append(" takes nothing returns integer");
		result.append("\n");
		result.append("local integer instance=");
		result.append(this.getGlobalVariableName());
		result.append("[0]");
		result.append("\n");
		result.append("if (");
		result.append(this.getGlobalVariableName());
		result.append("[0]==0) then");
		result.append("\n");
		result.append("set ");
		result.append(this.getGlobalVariableName());
		result.append("[0]=instance+1");
		result.append("\n");
		result.append("else");
		result.append("\n");
		result.append("set ");
		result.append(this.getGlobalVariableName());
		result.append("[0]=");
		result.append(this.getGlobalVariableName());
		result.append("[instance]");
		result.append("\n");
		result.append("endif\n");
		result.append("return instance");
		result.append("\n");
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
		result.append("\n");
		result.append("set ");
		result.append(this.getGlobalVariableName());
		result.append("[this]=");
		result.append(this.getGlobalVariableName());
		result.append("[0]");
		result.append("\n");
		result.append("set ");
		result.append(this.getGlobalVariableName());
		result.append("[0]=this");
		result.append("\n");
		result.append("endfunction");
		
		return result.toString();
	}
	
	public ClassDefaultAllocator(String className) {
		this.name = className;
	}
	
	public Stack<String> getGlobals() {
		Stack<String> globals = new Stack<String>();
		globals.push("integer array " + this.getGlobalVariableName());
		return globals;
	}
	
}
