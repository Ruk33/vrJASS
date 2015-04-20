package symbol;

import org.antlr.v4.runtime.Token;

public class VariableSymbol {

	protected String name;
	protected String type;
	protected boolean global;
	protected boolean array;
	protected String value;
	protected Token token;
	protected Visibility visibility;
	protected String scopeName;
	
	public VariableSymbol(
			String name,
			String type,
			boolean global,
			boolean array,
			String value,
			Token token,
			Visibility visibility,
			String scopeName) {
		this.name = name;
		this.type = type;
		this.global = global;
		this.array = array;
		this.value = value;
		this.token = token;
		this.visibility = visibility;
		this.scopeName = scopeName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public boolean isGlobal() {
		return this.global;
	}
	
	public boolean isArray() {
		return this.array;
	}
	
	public int getLine() {
		return this.token.getLine();
	}
	
	public int getCharPositionInLine() {
		return this.token.getCharPositionInLine();
	}
	
	public Token getToken() {
		return this.token;
	}

	public Visibility getVisibility() {
		return this.visibility;
	}

	public Object getScopeName() {
		return this.scopeName;
	}
	
}
