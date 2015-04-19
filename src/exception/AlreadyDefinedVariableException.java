package exception;

import org.antlr.v4.runtime.Token;

import symbol.VariableSymbol;

public class AlreadyDefinedVariableException extends CompileException {

	protected String name;
	protected VariableSymbol variable;
	
	public AlreadyDefinedVariableException(Token token, VariableSymbol variable) {
		super(token);
		
		this.name = token.getText();
		this.variable = variable;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				String.format(
					" Variable <%s> already defined in %d:%d",
					this.name,
					this.variable.getLine(),
					this.variable.getCharPositionInLine()
				);
	}

}
