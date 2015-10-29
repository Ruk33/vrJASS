package com.ruke.vrjassc.translator;

public class NormalStrategy extends Translator {

	private StringBuilder globals = new StringBuilder();
	private StringBuilder output = new StringBuilder();

	private int propertyEnum;
	
	public String getOutput() {
		return String.format(
				"globals\n"
				+ "%s\n"
				+ "endglobals\n"
				+ "%s",
				this.globals.toString(),
				this.output.toString()).trim();
	}

}
