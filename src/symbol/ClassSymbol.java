package symbol;

import org.antlr.v4.runtime.Token;

public class ClassSymbol extends Symbol {

	public ClassSymbol(
			String name,
			Visibility visibility,
			Symbol parent,
			Token token) {
		super(name, name, PrimitiveType.CLASS, visibility, parent, token);
	}
	
	@Override
	public String getFullName() {
		return "struct_" + super.getFullName();
	}

}
