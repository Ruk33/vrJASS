package exception;

import org.antlr.v4.runtime.Token;

public class IncorrectVariableTypeException extends CompileException {

	protected String name;
	protected String expectedType;
	protected String givenType;
	
	public IncorrectVariableTypeException(
			Token token, String expectedType, String givenType) {
		super(token);
		
		this.name = token.getText();
		this.expectedType = expectedType;
		this.givenType = givenType;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				String.format(
					" Variable <%s> must have a value of type <%s>, but <%s> type given",
					this.name,
					this.expectedType,
					this.givenType
				);
	}

}
