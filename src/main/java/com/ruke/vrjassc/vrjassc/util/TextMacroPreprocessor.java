package com.ruke.vrjassc.vrjassc.util;

import java.util.HashMap;
import java.util.Iterator;

public class TextMacroPreprocessor implements PreprocessorAction {
	
	private class TextMacro {
		private String name;
		private String code;
		private HashMap<String, String> variables;
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
		public void setCode(String code) {
			this.code = code;
		}
		
		public String getCode() {
			return this.code;
		}
		
		public void setVariable(String name, String value) {
			this.variables.put(name, value);
		}
		
		public HashMap<String, String> getVariables() {
			return this.variables;
		}
		
		public String render() {
			String output = this.code;
			
			for (String variable : this.variables.keySet()) {
				output = output.replace("$" + variable + "$", this.variables.get(variable));
			}
			
			return output;
		}
	}
	
	protected HashMap<String, TextMacro> textmacros = new HashMap<String, TextMacro>();
	protected TextMacro textmacro;
	
	@Override
	public void setPreprocessor(Preprocessor preprocessor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recognitionPhase(String line) {
		if (line.startsWith("endtextmacro")) {
			this.textmacros.put(this.textmacro.getName(), this.textmacro);
			this.textmacro = null;
		} else if (line.startsWith("textmacro")) {
			this.textmacro = new TextMacro();
			
			this.textmacro.setName(line.split(" ")[2]);
			this.textmacro.setCode("");
			
			for (String param : line.split("takes")[1].split(",")) {
				this.textmacro.setVariable(param, "");
			}
			
			System.out.println(this.textmacro.getVariables().keySet());
		} else {
			if (this.textmacro != null) {
				this.textmacro.setCode(
					this.textmacro.getCode() + line + System.lineSeparator()
				);
			}
		}
	}

	@Override
	public String replacePhase(String line) {
		String name;
		Iterator<String> params;
		
		if (line.startsWith("runtextmacro")) {
			name = line.replaceAll("(?i)//! runtextmacro (.+) *\\(.+\\)", "$1");
			this.textmacro = this.textmacros.get(name);
						
			if (this.textmacro != null) {
				params = this.textmacro.getVariables().keySet().iterator();
				
				for (String arg : line.replaceAll("(?i).+\\((.+)\\)", "$1").split(",")) {
					this.textmacro.setVariable(params.next(), arg);
				}
			}
			
			return this.textmacro.render();
		}
		
		return line;
	}

}
