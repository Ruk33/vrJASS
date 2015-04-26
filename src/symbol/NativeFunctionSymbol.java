package symbol;

import org.antlr.v4.runtime.Token;

public class NativeFunctionSymbol extends FunctionSymbol {

	public NativeFunctionSymbol(String name, String type, Symbol parent, Token token) {
		super(name, type, null, parent, token);
	}

}
