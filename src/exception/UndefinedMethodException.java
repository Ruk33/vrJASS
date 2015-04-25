package exception;

import org.antlr.v4.runtime.Token;

import symbol.Symbol;

public class UndefinedMethodException extends CompileException {
	
	protected String name;
	
	protected Symbol _class;
	
	public UndefinedMethodException(Token token, Symbol _class) {
		super(token);
		
		this.name = token.getText();
		this._class = _class;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() +
				String.format(" Class <%s> does not have a method called <%s>",
					this._class.getName(),
					this.name
				);
	}

}
