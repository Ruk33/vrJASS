package exception;

import org.antlr.v4.runtime.Token;

public class NoFunctionException extends CompileException {

	protected String name;
	
	public NoFunctionException(Token token) {
		super(token);
		this.name = token.getText();
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				String.format(" Element <%s> is not a function", this.name);
	}

}
