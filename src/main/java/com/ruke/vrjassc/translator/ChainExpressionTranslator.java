package com.ruke.vrjassc.translator;

import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;
import com.ruke.vrjassc.vrjassc.util.HashtableFunctionGetter;
import com.ruke.vrjassc.vrjassc.util.VariableTypeDetector;

import java.util.Stack;

public class ChainExpressionTranslator {
	
	private class Chainable {
		protected Symbol symbol;
		protected String index;
		protected String key;
		
		public Chainable(Symbol symbol, String index, String key) {
			this.symbol = symbol;
			this.index = index;
			
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
			
			if (this.symbol.hasModifier(Modifier.STATIC)) {
				return this.key + "[" + this.index + "]";
			}
			
			return String.format("%s*8191-IMinBJ(%s,8191)", this.key, this.index);
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
	
	protected String hashtableName;
	protected String value;
	protected Stack<Chainable> chain;
	
	public ChainExpressionTranslator() {
		this.chain = new Stack<ChainExpressionTranslator.Chainable>();
	}
	
	public ChainExpressionTranslator append(Symbol symbol, String index, String key) {
		this.chain.push(new Chainable(symbol, index, key));
		return this;
	}
	
	protected String buildGetter() {
		Stack<String> args = new Stack<String>();
		Chainable property = this.chain.pop();
		String loadFunc = "";
		Chainable ch;
		
		if (property.getSymbol() != null) {
			if (property.getSymbol().hasModifier(Modifier.STATIC)) {
				this.chain.clear();
				return property.getKey();
			}
			
			loadFunc = HashtableFunctionGetter.getLoadFunction(
				property.getSymbol().getType()
			);
		} else {
			loadFunc = HashtableFunctionGetter.getLoadFunction(null);
		}
		
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
		
		return loadFunc + "(" + String.join(",", args) + ")";
	}
	
	protected String buildSetter() {
		Chainable last = this.chain.peek();
		String result = this.buildGetter();
		
		if (last.getSymbol() != null && last.getSymbol().hasModifier(Modifier.STATIC)) {
			result += "=" + this.value;
		} else {
			Type symbolType = null;
			
			if (last.getSymbol() != null) {
				symbolType = last.getSymbol().getType();
			}
			
			result = result.replaceFirst(
				HashtableFunctionGetter.getLoadFunction(symbolType), 
				HashtableFunctionGetter.getSaveFunction(symbolType)
			);
			
			result = result.substring(0, result.length()-1) + "," + this.value + ")";
		}
		
		return result;
	}
	
	public String getHashtableName() {
		return this.hashtableName;
	}
	
	public ChainExpressionTranslator setHashtableName(String name) {
		this.hashtableName = name;
		return this;
	}
	
	public ChainExpressionTranslator setValue(String value) {
		this.value = value;
		return this;
	}
	
	public String build() {
		String result;
		
		if (this.chain.size() == 1) {
			result = this.chain.pop().getKey();
		} else if (this.value == null) {
			result = this.buildGetter();
		} else {
			result = this.buildSetter();
			this.value = null;
		}
		
		return result;
	}
	
}
