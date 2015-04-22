package symbol;

import org.antlr.v4.runtime.Token;

public class MethodSymbol extends FunctionSymbol {

	public MethodSymbol(
			String scopeName,
			String name,
			String returnType,
			Visibility visibility,
			Token token) {
		super(scopeName, name, returnType, visibility, token);
	}

}
