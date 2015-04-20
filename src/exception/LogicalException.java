package exception;

import org.antlr.v4.runtime.Token;

public class LogicalException extends CompileException {

	public LogicalException(Token token) {
		super(token);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				" Logical operator or/and can only be used with booleans";
	}

}
