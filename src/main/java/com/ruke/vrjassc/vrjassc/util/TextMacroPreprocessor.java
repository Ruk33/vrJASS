package com.ruke.vrjassc.vrjassc.util;

import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ruke.vrjassc.vrjassc.exception.IncorrectArgumentsTextmacroException;
import com.ruke.vrjassc.vrjassc.exception.UndefinedTextmacroException;

public class TextMacroPreprocessor implements PreprocessorAction {

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
	
	protected HashMap<String, Macro> macros = new HashMap<String, Macro>();
		
	public boolean hasTextMacros(String code) {
		return code.contains("textmacro ");
	}

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

	/**
	 * Find and register the macros in the hashmap
	 * Also, remove the macro itself from the output
	 */
	protected String findTextMacros(String code) {
		Matcher m = Pattern.compile("(?i)(\\/\\/\\! *textmacro *[\\S\\s]*?endtextmacro)").matcher(code);
		String match;
		
		String name;
		Stack<String> params;
		String body;
		
		while (m.find()) {
			match = m.group();
			
			name = match.split(" ")[2]; // //![0] textmacro[1] name[2]
			params = new Stack<String>();
			body = match
					// remove header
					.replaceAll(
						"(?i)\\/\\/\\! *textmacro *.+", ""
					)
					// remove footer
					.replaceAll(
						"(?i)\\/\\/! *endtextmacro", ""
					);
			
			// first line, split by "takes"
			for (String param : this.getParametersFromText(match.split("\n")[0])) {
				params.push(param.trim());
			}

			this.macros.put(name, new Macro(name, params, body));
			code = code.replace(match, "");
		}
		
		return code;
	}

	protected String replaceRunTextMacro(String code) {
		Matcher m = Pattern.compile("(?i)(\\/\\/\\! *runtextmacro *.+)").matcher(code);
		
		String match;
		
		String name;
		Macro macro;
		Stack<String> args;
		String body;
		
		while (m.find()) {
			match = m.group();
			
			name = match.replaceAll("(?i)//! *runtextmacro *(.+) *\\(.+\\)", "$1");
			macro = this.macros.get(name);

			if (macro == null) {
				throw new UndefinedTextmacroException(name);
			}

			args = this.getArgumentsFromText(match);

			if (args.size() != macro.getParams().size()) {
				throw new IncorrectArgumentsTextmacroException(name);
			}
			
			body = macro.getBody();
			
			for (String param : macro.getParams()) {
				body = body.replace("$" + param + "$", args.pop());
			}
			
			code = code.replace(match, body);
		}
		
		return code;
	}

	@Override
	public String run(String code) {
		if (!this.hasTextMacros(code)) {
			return code;
		}
		
		return this.replaceRunTextMacro(this.findTextMacros(code));
	}

}
