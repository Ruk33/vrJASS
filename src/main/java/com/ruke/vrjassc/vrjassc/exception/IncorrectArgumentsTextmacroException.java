package com.ruke.vrjassc.vrjassc.exception;

public class IncorrectArgumentsTextmacroException extends CompileException {

	protected String name;

	public IncorrectArgumentsTextmacroException(String name) {
		super(null);
		this.name = name;
	}

	@Override
	public String getMessage() {
		return String.format(
				"Incorrect amount of arguments passed to textmacro <%s>",
				this.name);
	}

}
