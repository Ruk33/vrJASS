package exception;

import org.antlr.v4.runtime.Token;

import symbol.FunctionSymbol;

public class NoReturnFunctionException extends CompileException {

	protected FunctionSymbol function;
	
	public NoReturnFunctionException(Token token, FunctionSymbol function) {
		super(token);
		this.function = function;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				String.format(
					" Function <%s> must return <%s>",
					this.function.getName(),
					this.function.getType()
				);
	}

}
