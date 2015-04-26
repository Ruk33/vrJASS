package exception;

import org.antlr.v4.runtime.Token;

public class SetConstantVariableException extends CompileException {

	protected String name;
	
	public SetConstantVariableException(Token token) {
		super(token);
		this.name = token.getText();
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				String.format(
					" Variable <%s> is constant, its value can not change",
					this.name
				);
	}

}
