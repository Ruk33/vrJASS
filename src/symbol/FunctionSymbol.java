package symbol;

import java.util.Stack;

import org.antlr.v4.runtime.Token;

public class FunctionSymbol {
	protected String name;
	protected Stack<String> params;
	protected String returnType;
	protected Token token;
	
	public FunctionSymbol(String name, String returnType, Token token) {
		this.name = name;
		this.params = new Stack<String>();
		this.returnType = returnType;
		this.token = token;
	}
	
	public FunctionSymbol addParamType(String paramType) {
		this.params.push(paramType);
		return this;
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
	
	public int getLine() {
		return this.token.getLine();
	}
	
	public int getCharPositionInLine() {
		return this.token.getCharPositionInLine();
	}
}