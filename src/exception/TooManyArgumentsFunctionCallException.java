package exception;

import org.antlr.v4.runtime.Token;

public class TooManyArgumentsFunctionCallException extends CompileException {

	protected String name;
	
	public TooManyArgumentsFunctionCallException(Token token) {
		super(token);
		this.name = token.getText();
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + 
				String.format(
					" Too many arguments passed to function <%s>", 
					this.name
				);
	}

}
