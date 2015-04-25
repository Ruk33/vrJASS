package symbol;

import java.util.Stack;

public class FunctionSymbol extends Symbol {

	protected Stack<Symbol> params;
	
	public FunctionSymbol(
			String name,
			String type,
			Visibility visibility,
			Symbol parent) {
		super(name, type, PrimitiveType.FUNCTION, visibility, parent);
		this.params = new Stack<Symbol>();
	}

	@Override
	public Symbol addChild(Symbol child) {
		if (child instanceof ParameterSymbol) {
			this.params.push(child);
		}
		
		return super.addChild(child);
	}
	
	public Stack<Symbol> getParams() {
		return this.params;
	}
	
}