package com.ruke.vrjassc.vrjassc.util;

import java.util.HashMap;

public class ModulePreprocessor {
	
	protected String output;
	protected String code;
	protected HashMap<String, String> modules;
	
	public ModulePreprocessor(String code) {
		this.output = code;
		this.code = code;
		this.modules = new HashMap<String, String>();
		
		this.findModules();
		this.makeOutput();
	}
	
	protected void makeOutput() {
		String name;
		
		for (String line : this.code.split("\n")) {
			if (line.contains("implements")) {
				name = line.substring(line.lastIndexOf(" ") + 1);
				
				if (!this.modules.containsKey(name)) {
					continue;
				}
				
				this.output = this.output.replace(
					line,
					line + System.lineSeparator() + this.modules.get(name)
				);
			}
		}
	}
	
	public String getOutput() {
		return this.output;
	}
	
	protected void findModules() {
		boolean inModule = false;
		
		String name = null;
		String body = null;
		
		for (String line : this.code.split("\n")) {
			if (line.contains("endmodule")) {
				this.modules.put(name, body);
				inModule = false;
			} else if (line.contains("module")) {
				name = line.substring(line.lastIndexOf(" ") + 1);
				body = "";
				
				inModule = true;
			} else {
				if (inModule) {
					body += line + System.lineSeparator();
				}
			}
		}
	}

}
