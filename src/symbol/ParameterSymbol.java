package symbol;

public class ParameterSymbol extends VariableSymbol {

	public ParameterSymbol(
			String name,
			String type,
			Symbol parent) {
		super(name, type, false, false, Visibility.PRIVATE, parent);
	}

}
