package symbol;

import org.antlr.v4.runtime.Token;

public class MethodSymbol extends FunctionSymbol implements ClassMemberSymbol {

	protected boolean _static;
	
	public MethodSymbol(
			String name,
			String type,
			boolean isStatic,
			Visibility visibility,
			Symbol parent,
			Token token) {
		super(name, type, visibility, parent, token);
		
		if (this.visibility == null) {
			this.visibility = Visibility.PUBLIC;
		}

		this._static = isStatic;
	}

	@Override
	public boolean isStatic() {
		return this._static;
	}

}
