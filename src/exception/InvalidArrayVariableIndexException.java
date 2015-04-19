package exception;

import org.antlr.v4.runtime.Token;

public class InvalidArrayVariableIndexException extends CompileException {

	public InvalidArrayVariableIndexException(Token token) {
		super(token);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + " Invalid index (only integer type)";
	}

}
