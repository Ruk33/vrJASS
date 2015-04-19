package exception;

import org.antlr.v4.runtime.Token;

public class InitializeArrayVariableException extends CompileException {

	public InitializeArrayVariableException(Token token) {
		super(token);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + " Arrays can not be initialized on declaration";
	}

}
