package com.ruke.vrjassc.vrjassc;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.exception.IncompatibleTypeException;
import com.ruke.vrjassc.vrjassc.exception.IncorrectArgumentCountException;
import com.ruke.vrjassc.vrjassc.exception.MissReturnException;
import com.ruke.vrjassc.vrjassc.exception.NoFunctionException;
import com.ruke.vrjassc.vrjassc.exception.UndefinedSymbolException;

public class FunctionTest extends TestHelper {

	@Test
	public void useUndefinedVariable() {
		this.expectedEx.expect(UndefinedSymbolException.class);
		this.expectedEx.expectMessage("2:20 Element <x> is not defined");
		this.run("function foo takes nothing returns nothing\n"
					+ "local integer bar = x\n"
				+ "endfunction");
	}
	
	@Test
	public void undefinedReturnType() {
		this.expectedEx.expect(UndefinedSymbolException.class);
		this.expectedEx.expectMessage("1:35 Element <phony> is not defined");
		this.run("function foo takes nothing returns phony\n"
				+ "endfunction");
	}
	
	@Test
	public void incorrectReturnType() {
		this.expectedEx.expect(IncompatibleTypeException.class);
		this.expectedEx.expectMessage("2:7 Element <foo> must have/return a value of type <integer> but given <boolean>");
		this.run("function foo takes nothing returns integer\n"
					+ "return false\n"
				+ "endfunction");
	}
	
	@Test
	public void returnNothing() {
		this.expectedEx.none();
		this.run("function foo takes nothing returns nothing\n"
					+ "return\n"
				+ "endfunction");
	}
	
	@Test
	public void callNoFunction() {
		this.expectedEx.expect(NoFunctionException.class);
		this.expectedEx.expectMessage("3:5 Element <i> is not a function");
		this.run("function foo takes nothing returns nothing\n"
					+ "local integer i\n"
					+ "call i()\n"
				+ "endfunction");
	}
	
	@Test
	public void noReturn() {
		this.expectedEx.expect(MissReturnException.class);
		this.expectedEx.expectMessage("1:9 Function <foo> is missing return of type <integer>");
		this.run("function foo takes nothing returns integer\n"
				+ "endfunction");
	}
	
	@Test
	public void noReturnAlternative() {
		this.expectedEx.expect(MissReturnException.class);
		this.expectedEx.expectMessage("1:9 Function <foo> is missing return of type <integer>");
		this.run("function foo takes nothing returns integer\n"
					+ "if false then\n"
						+ "return 1\n"
					+ "else\n"
						+ "if false then\n"
							+ "return 2\n"
						+ "elseif true then\n"
						+ "else\n"
							+ "return 3\n"
						+ "endif\n"
					+ "endif\n"
				+ "endfunction");
	}
	
	@Test
	public void returnOnIfStatement() {
		this.expectedEx.none();
		this.run("function foo takes nothing returns integer\n"
					+ "if true then\n"
						+ "if false then\n"
							+ "return 1\n"
						+ "else\n"
							+ "return -1\n"
						+ "endif\n"
					+ "else\n"
						+ "return 0\n"
					+ "endif\n"
				+ "endfunction");
	}
	
	@Test
	public void callWithTooFewArguments() {
		this.expectedEx.expect(IncorrectArgumentCountException.class);
		this.expectedEx.expectMessage("4:5 Incorrect amount of arguments passed to function <foo>");
		this.run("function foo takes integer a, integer b returns nothing\n"
				+ "endfunction\n"
				+ "function bar takes nothing returns nothing\n"
					+ "call foo(1)\n"
				+ "endfunction");
	}
	
	@Test
	public void callWithTooManyArguments() {
		this.expectedEx.expect(IncorrectArgumentCountException.class);
		this.expectedEx.expectMessage("4:5 Incorrect amount of arguments passed to function <foo>");
		this.run("function foo takes integer a, integer b returns nothing\n"
				+ "endfunction\n"
				+ "function bar takes nothing returns nothing\n"
					+ "call foo(1,2,3)\n"
				+ "endfunction");
	}
	
	@Test
	public void callWithIncompatibleArguments() {
		this.expectedEx.expect(IncompatibleTypeException.class);
		this.expectedEx.expectMessage("4:5 Element <b> must have/return a value of type <integer> but given <boolean>");
		this.run("function foo takes integer a, integer b returns nothing\n"
				+ "endfunction\n"
				+ "function bar takes nothing returns nothing\n"
					+ "call foo(1,false)\n"
				+ "endfunction");
	}

}
