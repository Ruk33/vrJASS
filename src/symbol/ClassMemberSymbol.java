package symbol;

public class ClassMemberSymbol extends Symbol {

	protected boolean _static;
	
	public ClassMemberSymbol(
			String name,
			String type,
			boolean isStatic,
			PrimitiveType primitiveType,
			Visibility visibility,
			Symbol parent) {
		super(name, type, primitiveType, visibility, parent);
		this._static = isStatic;
	}
	
	public boolean isStatic() {
		return this._static;
	}

}
