package symbol;

import java.util.Stack;

import org.antlr.v4.runtime.Token;

public class FunctionSymbol {
	protected String scopeName;
	protected String name;
	protected Stack<String> params;
	protected String returnType;
	protected Token token;
	protected Visibility visibility;
	
	public FunctionSymbol(String scopeName, String name, String returnType, Visibility visibility, Token token) {
		this.scopeName = scopeName;
		this.name = name;
		this.params = new Stack<String>();
		this.returnType = returnType;
		this.visibility = visibility;
		this.token = token;
	}
	
	public FunctionSymbol addParamType(String paramType) {
		this.params.push(paramType);
		return this;
	}
	
	public String getScopeName() {
		return this.scopeName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Stack<String> getParams() {
		return this.params;
	}
	
	public String getReturnType() {
		return this.returnType;
	}
	
	public Visibility getVisibility() {
		return this.visibility;
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