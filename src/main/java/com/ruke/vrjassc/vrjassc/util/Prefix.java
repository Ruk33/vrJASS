package com.ruke.vrjassc.vrjassc.util;

import org.antlr.v4.runtime.Token;

/**
 * @deprecated
 * @author Ruke
 *
 */
public class Prefix {

	public String getForScope(boolean _private, String scopeName) {
		if (scopeName == null || scopeName.equals("")) {
			return "";
		}

		if (_private) {
			return scopeName + "__";
		}

		return scopeName + "_";
	}

	public String getForScope(Token visibility, String scopeName) {
		boolean _private = false;

		if (visibility == null) {
			return "";
		} else {
			_private = visibility.getText().equals("private");
		}

		return this.getForScope(_private, scopeName);
	}

	public String getForClass(boolean _private, String scopeName,
			String className) {
		if (scopeName == null || scopeName.equals("")) {
			scopeName = className;
			return "struct_" + this.getForScope(_private, scopeName);
		}

		return "struct_" + this.getForScope(_private, scopeName) + className
				+ "_";
	}

	public String getForClass(Token visibility, String scopeName,
			String className) {
		boolean _private = false;

		if (visibility != null) {
			_private = visibility.getText().equals("private");
		}

		return this.getForClass(_private, scopeName, className);
	}

}
