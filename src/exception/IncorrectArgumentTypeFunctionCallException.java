package exception;

import org.antlr.v4.runtime.Token;

public class IncorrectArgumentTypeFunctionCallException extends
		CompileException {

	protected String expectedType;
	protected String givenType;
	
	public IncorrectArgumentTypeFunctionCallException(
			Token token, String expectedType, String givenType) {
		super(token);
		
		this.expectedType = expectedType;
		this.givenType = givenType;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				String.format(
					" Incorrect argument type, given <%s> but should be <%s>",
					this.givenType,
					this.expectedType
				);
	}

}
