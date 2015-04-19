package exception;

import org.antlr.v4.runtime.Token;

import symbol.FunctionSymbol;

public class AlreadyDefinedFunctionException extends CompileException {

	protected String name;
	protected FunctionSymbol func;
	
	public AlreadyDefinedFunctionException(Token token, FunctionSymbol func) {
		super(token);
		
		this.name = token.getText();
		this.func = func;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				String.format(
					" Function <%s> already defined in %d:%d",
					this.name,
					this.func.getLine(),
					this.func.getCharPositionInLine()
				);
	}

}
