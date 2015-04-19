package exception;

import org.antlr.v4.runtime.Token;

public class UndefinedFunctionException extends CompileException {

	protected String name;
	
	public UndefinedFunctionException(Token token) {
		super(token);
		this.name = token.getText();
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + 
				String.format(" Function <%s> is not defined", this.name);
	}

}
