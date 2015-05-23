package com.ruke.vrjassc.vrjassc.util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModulePreprocessor implements PreprocessorAction {
	
	protected HashMap<String, String> modules = new HashMap<String, String>();
	
	protected String findModules(String code) {
		Matcher m = Pattern.compile("(?i)(module *[\\s\\S]*?endmodule)").matcher(code);
		
		String match;
		String firstLine;
		
		String name;
		String body;
		
		while (m.find()) {
			match = m.group();
			firstLine = match.split("\n")[0];
			
			name = firstLine.substring(firstLine.lastIndexOf(" ") + 1);
			body = match.replace(firstLine, "").replace("endmodule", "");
			
			this.modules.put(name, body);
		}
		
		return code;
	}
	
	protected String implementModules(String code) {
		Matcher m = Pattern.compile("(?i)(implement *.+)").matcher(code);
		String match;
		
		String name;
		String body;
		
		while (m.find()) {
			match = m.group();
			name = match.split(" ")[1];
			body = this.modules.get(name);
			
			if (body == null) {
				continue;
			}
			
			code = code.replace(match, body);
		}
		
		return code;
	}

	@Override
	public String run(String code) {
		return this.implementModules(this.findModules(code));
	}

}
