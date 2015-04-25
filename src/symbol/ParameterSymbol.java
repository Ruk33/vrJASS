package symbol;

import org.antlr.v4.runtime.Token;

public class ParameterSymbol extends VariableSymbol {

	public ParameterSymbol(
			String name,
			String type,
			Symbol parent,
			Token token) {
		super(name, type, false, false, Visibility.PRIVATE, parent, token);
	}

}
