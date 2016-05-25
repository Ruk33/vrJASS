package com.ruke.vrjassc.vrjassc.validator;

import com.ruke.vrjassc.vrjassc.exception.*;
import com.ruke.vrjassc.vrjassc.util.TestHelper;
import org.junit.Test;

public class FunctionTest extends TestHelper {

	@Test
	public void resolveFromAnonFunction() {
		this.expectedEx.none();
		this.run(
		"library foo\n" +
			"globals\n" +
				"boolean bar\n" +
			"end\n" +
			"function baz\n" +
				"set bar = false\n" +
				"call TimerStart(CreateTimer(), 42, false, function\n" +
					"set bar = true\n" +
				"end)\n" +
			"end\n" +
		"end"
		);
	}

	@Test
	public void emptyLines() {
		this.expectedEx.none();
		this.run("public function bool takes boolean b, integer line\n" +
			     "   if (b == false) then\n" +
			     "       //local string expected = \"false\"\n" +
			     "       \n" +
			     "       //if (b) then\n" +
			     "       //    set expected = \"true\"\n" +
			     "       //endif\n" +
			            
			     "       //call print(\"[FAIL] Expected \" + \" on line \" + I2S(line))\n" +
			     "   endif\n" +
			     "endfunction\n" +
			
			     "public function toBeFalse takes boolean b, integer line\n" +
			     "    call bool(b == false, line)\n" +
			     "endfunction");
	}
	
	@Test
	public void declareVariableBeforeUsing() {
		this.expectedEx.expect(InvalidStatementException.class);
		this.expectedEx.expectMessage("2:9 Variables must be declared before use");
		this.run("function foo takes nothing returns nothing\n"
					+ "call I2S(bar)\n"
					+ "local integer bar\n"
				+ "endfunction");
	}
	
	@Test
	public void validExitwhen() {
		this.expectedEx.none();
		this.run("function foo takes nothing returns nothing\n"
					+ "if false then\n"
						+ "loop\n"
							+ "if true then\n"
								+ "exitwhen false\n"
							+ "endif\n"
							+ "exitwhen true\n"
						+ "endloop\n"
					+ "endif\n"
				+ "endfunction");
	}

	@Test
	public void invalidContinue() {
		this.expectedEx.expect(InvalidStatementException.class);
		this.expectedEx.expectMessage("2:0 Can only be used inside of loops");
		this.run("function foo takes nothing returns nothing\n"
			+ "continue\n"
			+ "endfunction");
	}
	
	@Test
	public void invalidBreak() {
		this.expectedEx.expect(InvalidStatementException.class);
		this.expectedEx.expectMessage("2:0 Can only be used inside of loops");
		this.run("function foo takes nothing returns nothing\n"
					+ "break\n"
				+ "endfunction");
	}
	
	@Test
	public void invalidExitwhen() {
		this.expectedEx.expect(InvalidStatementException.class);
		this.expectedEx.expectMessage("2:0 Can only be used inside of loops");
		this.run("function foo takes nothing returns nothing\n"
					+ "exitwhen true\n"
				+ "endfunction");
	}
	
	@Test
	public void concatenateString() {
		this.expectedEx.none();
		this.run("function foo takes nothing returns nothing\n"
					+ "local string s = \"foo\" + \"bar\"\n"
				+ "endfunction");
	}
	
	@Test
	public void invalidStringConcatenation() {
		this.expectedEx.expect(InvalidStringConcatenationException.class);
		this.expectedEx.expectMessage("2:17 Invalid string concatenation");
		this.run("function foo takes nothing returns nothing\n"
					+ "local string s = \"foo\" + 2\n"
				+ "endfunction");
	}
	
	@Test
	public void invalidDivisionOperation() {
		this.expectedEx.expect(InvalidMathException.class);
		this.expectedEx.expectMessage("2:20 Not a valid math operation");
		this.run("function foo takes nothing returns nothing\n"
					+ "local integer bar = 1/\"s\"\n"
				+ "endfunction");
	}
	
	@Test
	public void invalidMultiplicationOperation() {
		this.expectedEx.expect(InvalidMathException.class);
		this.expectedEx.expectMessage("2:20 Not a valid math operation");
		this.run("function foo takes nothing returns nothing\n"
					+ "local integer bar = 1*\"s\"\n"
				+ "endfunction");
	}
	
	@Test
	public void invalidSubstractionOperation() {
		this.expectedEx.expect(InvalidMathException.class);
		this.expectedEx.expectMessage("2:20 Not a valid math operation");
		this.run("function foo takes nothing returns nothing\n"
					+ "local integer bar = 1-\"s\"\n"
				+ "endfunction");
	}
	
	@Test
	public void invalidAdditionOperation() {
		this.expectedEx.expect(InvalidMathException.class);
		this.expectedEx.expectMessage("2:20 Not a valid math operation");
		this.run("function foo takes nothing returns nothing\n"
					+ "local integer bar = 1+\"s\"\n"
				+ "endfunction");
	}
	
	@Test
	public void _native() {
		this.expectedEx.none();
		this.run("function foo takes nothing returns nothing\n"
					+ "call GetLocalPlayer()\n"
				+ "endfunction");
	}
	
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
