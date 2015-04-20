package exception;

import org.antlr.v4.runtime.Token;

public class NoScopeVisibilityException extends CompileException {

	protected String name;
	
	public NoScopeVisibilityException(Token token) {
		super(token);
		this.name = token.getText();
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				String.format(
					" Function <%s> must be inside of an scope to declare visibility",
					this.name
				);
	}

}
