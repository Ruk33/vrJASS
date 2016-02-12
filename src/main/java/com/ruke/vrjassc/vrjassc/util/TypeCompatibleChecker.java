package com.ruke.vrjassc.vrjassc.util;

public class TypeCompatibleChecker {

	public static boolean isCompatible(String a, String b) {
		if (a.equals(b)) {
			return true;
		}
		
		if (a.equals("thistype") && b.equals("integer")) {
			return true;
		}

		if (a.equals("real") && b.equals("integer")) {
			return true;
		}

		if (b.equals("real") && a.equals("integer")) {
			return true;
		}

		if (b.equals("null")) {
			// strings, code and handles can be null
			if (a.equals("string") || a.equals("code") || VariableTypeDetector.isHandle(a)) {
				return true;
			}
		}

		if (VariableTypeDetector.isHandle(a) && VariableTypeDetector.isHandle(b)) {
			return true;
		}

		return false;
	}

}
