package util;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class TextMacro {
	
	private class Macro {
		private String name;
		private Stack<String> params;
		private String body;
		
		public Macro(String name, Stack<String> params, String body) {
			this.name = name;
			this.params = params;
			this.body = body;
		}
		
		public String getBody() {
			return this.body;
		}
		
		public Stack<String> getParams() {
			return this.params;
		}
	}

	protected String code;
	
	protected String output;
	
	protected HashMap<String, Macro> macros;
	
	protected Stack<String> getParametersFromText(String text) {
		Stack<String> result = new Stack<String>();
		
		if (text.contains("takes")) {
			String params = text.split("takes")[1];
			
			for (String param : params.split(",")) {
				result.push(param.trim());
			}
		}
		
		return result;
	}
	
	protected Stack<String> getArgumentsFromText(String text) {
		Stack<String> result = new Stack<String>();
		String parenthesis = text.replaceAll("(?i).+\\((.+)\\)", "$1");
		
		for (String comma : parenthesis.split(",")) {
			result.push(comma.trim());
		}
		
		return result;
	}
	
	protected void findMacros() {
		String name = null;
		Stack<String> params = null;
		Stack<String> body = null;
		boolean inMacro = false;
		
		for (String line : this.code.split("\n")) {
			if (line.contains("//! textmacro")) {
				name = line.split(" ")[2];
				params = this.getParametersFromText(line);
				body = new Stack<String>();
				inMacro = true;
			} else if (line.contains("//! endtextmacro")) {
				this.macros.put(
					name, new Macro(name, params, String.join("\n", body))
				);
				
				inMacro = false;
			} else {
				if (inMacro) {
					body.push(line);
				}
			}
		}
	}
	
	protected void makeOutput() {
		Stack<String> result = new Stack<String>();
		boolean inMacro = false;
		Macro macro;
		String macroName;
		PriorityQueue<String> args;
		
		for (String line : this.code.split("\n")) {
			if (line.contains("//! textmacro")) {
				inMacro = true;
			} else if (line.contains("//! endtextmacro")) {
				inMacro = false;
			} else {
				if (inMacro) {
					continue;
				}
				
				if (line.contains("//! runtextmacro")) {
					macroName = line.replaceAll(
						"(?i)//! runtextmacro (.+) *\\(.+\\)", "$1"
					);
					
					macro = this.macros.get(macroName);
					args = new PriorityQueue<String>(this.getArgumentsFromText(line));
					line = macro.getBody();

					for (String param : macro.getParams()) {
						line = line.replace("$" + param + "$", args.peek());
					}
				}
				
				result.push(line);
			}
		}
		
		this.output = String.join(System.lineSeparator(), result);
	}
	
	public TextMacro(String code) {
		this.code = code;
		this.output = code;
		this.macros = new HashMap<String, Macro>();
		
		this.findMacros();
		this.makeOutput();
	}

	public String getOutput() {
		return this.output;
	}
	
}
