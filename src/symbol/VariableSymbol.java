package symbol;

import org.antlr.v4.runtime.Token;

public class VariableSymbol {

	protected String name;
	protected String type;
	protected boolean array;
	protected String value;
	protected Token token;
	
	public VariableSymbol(
			String name, String type, boolean array, String value, Token token) {
		this.name = name;
		this.type = type;
		this.array = array;
		this.value = value;
		this.token = token;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
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
	
}
