package symbol;

public class PropertySymbol extends VariableSymbol implements ClassMemberSymbol {

	protected boolean _static;
	
	public PropertySymbol(
			String name,
			String type,
			boolean isStatic,
			boolean isArray,
			Visibility visibility,
			Symbol parent) {
		super(name, type, isArray, false, visibility, parent);
		this._static = isStatic;
		
	}

	@Override
	public boolean isStatic() {
		return this._static;
	}

}
