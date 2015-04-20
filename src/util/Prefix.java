package util;

import org.antlr.v4.runtime.Token;

public class Prefix {

	public String get(Token visibility, String scopeName) {
		if (scopeName == null || scopeName.equals("")) {
			return "";
		}
		
		if (visibility == null) {
			return "";
		}
		
		if (visibility.getText().equals("private")) {
			return scopeName + "__";
		} else {
			return scopeName + "_";
		}
	}
	
}
