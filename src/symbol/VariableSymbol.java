package symbol;

public class VariableSymbol extends Symbol {
	
	protected boolean array;
	
	protected boolean global;
	
	public VariableSymbol(
			String name,
			String type,
			boolean isArray,
			boolean isGlobal,
			Visibility visibility,
			Symbol parent) {
		super(name, type, PrimitiveType.VARIABLE, visibility, parent);
		
		this.array = isArray;
		this.global = isGlobal;
	}
	
	public boolean isGlobal() {
		return this.global;
	}
	
	public boolean isArray() {
		return this.array;
	}
	
}
