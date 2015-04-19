package exception;

import org.antlr.v4.runtime.Token;

public class EqualNotEqualComparisonException extends CompileException {

	public EqualNotEqualComparisonException(Token token) {
		super(token);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				" Equal than and not equal than comparison can only be interchangeable with integers and reals";
	}

}
