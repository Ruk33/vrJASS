package com.ruke.vrjassc.vrjassc.util;

import java.util.Stack;

import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;

public class ChainExpressionTranslator {
	
	protected String hashtableName;
	protected String value;
	
	private class Chainable {
		protected Symbol symbol;
		protected String index;
		protected String key;
		
		public Chainable(Symbol symbol, String index, String key) {
			this.symbol = symbol;
			
			if (symbol.hasModifier(Modifier.ARRAY)) {
				this.index = index;
			}
			
			if (key == null) {
				key = symbol.getName();
			}
			
			this.key = key;
		}
		
		public Symbol getSymbol() {
			return this.symbol;
		}
		
		public String getIndex() {
			return this.index;
		}
		
		public String getKey() {
			if (this.getIndex() == null) {
				return this.key;
			}
			
			return String.format("%s*8191-IMinBJ(%s,8191)", this.key, this.getIndex());
		}
		
		public boolean isSpecial() {
			Type type = this.symbol.getType();
						
			if (type != null) {
				if (VariableTypeDetector.isUserType(type.getName())) {
					return true;
				}
			}
			
			if (this.getIndex() != null) {
				return true;
			}
			
			return false;
		}
	}
	
	protected Stack<Chainable> chain = new Stack<Chainable>();
	
	public ChainExpressionTranslator append(Symbol symbol, String index, String key) {
		this.chain.push(new Chainable(symbol, index, key));
		return this;
	}
		
	public String buildGetter() {
		Stack<String> args = new Stack<String>();
		Chainable property = this.chain.pop();
		Chainable ch;
		
		args.push(this.getHashtableName());
		
		while (this.chain.size() != 0) {
			ch = this.chain.peek();
			
			if (this.chain.size() != 1 && ch.isSpecial()) {
				args.add(this.buildGetter());
				break;
			}
			
			args.push(ch.getKey());
			this.chain.pop();
		}
		
		args.push(property.getKey());
		
		return "LoadInteger(" + String.join(",", args) + ")";
	}

	public String getHashtableName() {
		return this.hashtableName;
	}
	
	public ChainExpressionTranslator setHashtableName(String name) {
		this.hashtableName = name;
		return this;
	}

	public String buildSetter(String value) {
		String result = this.buildGetter().replaceFirst("Load", "Save");
		return result.substring(0, result.length()-1)+","+value+")";
	}
	
	public String build(String value) {
		if (value == null) {
			return this.buildGetter();
		}
		
		return this.buildSetter(value);
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
