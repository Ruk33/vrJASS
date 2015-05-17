package com.ruke.vrjassc.vrjassc.util;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassPreprocessor implements PreprocessorAction {
		
	private String getAllocateBody() {
		Stack<String> result = new Stack<String>();
		
		result.push("private static thistype array recycle");
		result.push("private static method allocate takes nothing returns thistype");
		result.push("local thistype instance = thistype.recycle[0]");
		result.push("if thistype.recycle[instance] then");
		result.push("set thistype.recycle[0] = thistype.recycle[instance]");
		result.push("else");
		result.push("set thistype.recycle[0] = instance + 1");
		result.push("endif");
		result.push("call instance.initializeProperties()");
		result.push("return instance");
		result.push("endmethod");
		
		return String.join(System.lineSeparator(), result);
	}
	
	private String getDeallocateBody() {
		Stack<String> result = new Stack<String>();
		
		result.push("private method deallocate takes nothing returns nothing");
		result.push("set thistype.recycle[this] = thistype.recycle[0]");
		result.push("set thistype.recycle[0] = this");
		result.push("endmethod");
		
		return String.join(System.lineSeparator(), result);
	}
	
	private String getInitializePropertiesBody(String struct) {
		Matcher m = Pattern.compile("(?i)(?![a-zA-Z0-9_]+\\s+)([a-zA-Z0-9_]+) *= *(.+)").matcher(struct);
		Stack<String> result = new Stack<String>();
		
		result.push("private method initializeProperties takes nothing returns nothing");
		
		while (m.find()) {
			result.push("set this." + m.group());
		}
		
		result.push("endmethod");
		
		return String.join(System.lineSeparator(), result);
	}
	
	@Override
	public String run(String code) {
		if (code.contains("struct ")) {
			Matcher m = Pattern.compile("(?i)struct[\\S\\s]+?endstruct").matcher(code);
			String match;
			
			while (m.find()) {
				match = m.group();
				
				if (match.contains(" extends ")) {
					continue;
				}
				
				code = code.replace(
					match,
					match.replaceFirst(
						"\n",
						this.getInitializePropertiesBody(match)
						+ System.lineSeparator()
						+ this.getAllocateBody()
						+ System.lineSeparator()
						+ this.getDeallocateBody()
						+ System.lineSeparator()
					)
				);
			}
		}
		
		return code;
	}

}
