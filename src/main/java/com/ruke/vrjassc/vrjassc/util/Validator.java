package com.ruke.vrjassc.vrjassc.util;

import java.util.Stack;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.exception.AlreadyDefinedException;
import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.exception.IncompatibleTypeException;
import com.ruke.vrjassc.vrjassc.exception.IncorrectArgumentCountException;
import com.ruke.vrjassc.vrjassc.exception.InvalidExtendTypeException;
import com.ruke.vrjassc.vrjassc.exception.InvalidImplementTypeException;
import com.ruke.vrjassc.vrjassc.exception.InvalidTypeException;
import com.ruke.vrjassc.vrjassc.exception.NoAccessException;
import com.ruke.vrjassc.vrjassc.exception.UndefinedSymbolException;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.InterfaceSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Scope;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;

/**
 * Maintain all the validation (type compatible, amount of arguments
 * passed to a function, etc.) logic in one class
 * 
 * @author Ruke
 */
public class Validator {

	private CompileException exception;
	
	private Symbol validated;
	
	public Symbol getValidatedSymbol() {
		return this.validated;
	}
	
	public CompileException getException() {
		return this.exception;
	}
	
	/**
	 * Validates that a symbol must not be defined
	 * @param scope
	 * @param name
	 * @param token
	 * @return
	 */
	public boolean mustNotBeDefined(Scope scope, String name, Token token) {
		this.validated = scope.resolve(scope, name);
		
		if (this.validated != null) {
			this.exception = new AlreadyDefinedException(token, this.validated);
			return false;
		}
		
		return true;
	}

	/**
	 * 
	 * @param scope
	 * @param name
	 * @param token
	 * @return
	 */
	public boolean mustBeDefined(Scope scope, String name, Token token) {
		this.validated = scope.resolve(scope, name);
		
		if (this.validated == null) {
			this.exception = new UndefinedSymbolException(token, name);
			return false;
		}
		
		return true;
	}

	/**
	 * Symbol's type b must be compatible with symbol's a
	 * It will also verify that both types are defined
	 * @param a
	 * @param b
	 * @param token
	 * @return
	 */
	public boolean mustBeTypeCompatible(Symbol a, Symbol b, Token token) {
		this.validated = b;
		
		if (a.getType() == null) {
			this.exception = new InvalidTypeException(token, a);
			return false;
		}
		
		if (b.getType() == null) {
			this.exception = new InvalidTypeException(token, b);
			return false;
		}
		
		if (!a.isTypeCompatible(b)) {
			this.exception = new IncompatibleTypeException(token, a, b.getType());
			return false;
		}
		
		return true;
	}

	public boolean mustMatchArguments(FunctionSymbol function, Stack<Symbol> arguments, Token token) {
		this.validated = function;

		if (function.getParams().size() == arguments.size()) {
			int i = 0;
			
			for (Symbol argument : arguments) {
				if (!this.mustBeTypeCompatible(function.getParams().get(i), argument, token)) {
					return false;
				}
				
				i++;
			}
		} else {
			this.exception = new IncorrectArgumentCountException(token, function);
			return false;
		}
		
		return true;
	}

	public boolean mustHaveAccess(Scope scope, Symbol symbol, Token token) {
		this.validated = symbol;
		
		if (!scope.hasAccess(symbol)) {
			this.exception = new NoAccessException(token, scope, symbol);
			return false;
		}
		
		return true;
	}

	public boolean mustBeValidType(Symbol type, Token token) {
		this.validated = type;
		
		if (type instanceof Type == false) {
			this.exception = new InvalidTypeException(token, type);
			return false;
		}
		
		return true;
	}

	public boolean mustBeExtendableValid(Symbol symbol, Token token) {
		this.validated = symbol;
		
		if (symbol instanceof ClassSymbol == false) {
			this.exception = new InvalidExtendTypeException(token, symbol);
			return false;
		}
		
		return true;
	}

	public boolean mustBeImplementableTypeValid(Symbol symbol, Token token) {
		this.validated = symbol;
		
		if (symbol instanceof InterfaceSymbol == false) {
			this.exception = new InvalidImplementTypeException(token, symbol);
			return false;
		}
		
		return true;
	}

}