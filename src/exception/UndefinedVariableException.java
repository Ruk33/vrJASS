package exception;

import org.antlr.v4.runtime.Token;

public class UndefinedVariableException extends CompileException {

	protected String name;
	
	public UndefinedVariableException(Token token) {
		super(token);
		this.name = token.getText();
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				String.format(" Variable <%s> is not defined", this.name);
	}

}
