package exception;

import org.antlr.v4.runtime.Token;

public class UndefinedPropertyException extends CompileException {

	public UndefinedPropertyException(Token token) {
		super(token);
		// TODO Auto-generated constructor stub
	}

}
