package exception;

import org.antlr.v4.runtime.Token;

public class LessGreaterComparisonException extends CompileException {

	public LessGreaterComparisonException(Token token) {
		super(token);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				" Lower and greater than comparison can only be used with integers and reals";
	}

}
