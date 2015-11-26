package com.ruke.vrjassc.vrjassc.exception;

public class SyntaxErrorException extends CompileException {

	protected String msg;
	
	public SyntaxErrorException(int line, int charPos, String msg) {
		super(null);
		
		this.line = line;
		this.charPos = charPos;
		this.msg = msg;
	}
	
	@Override
	public String getErrorMessage() {
		return "Syntax error " + this.msg;
	}

}
