package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.Compile;

public class LocalVariableStatementTest {

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code = "function foo takes integer i returns integer" + System.lineSeparator()
				+ "local integer bar=2" + System.lineSeparator()
				+ "local integer nop" + System.lineSeparator()
				+ "return 1" + System.lineSeparator()
				+ "endfunction";

		assertEquals(code, compile.run(code));
	}

	@Test
	public void freeDeclaration() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "call foo()\n"
				+ "call foo()\n"
				+ "local integer a=5\n"
				+ "local integer b=6\n"
				+ "endfunction";

		String result = "function foo takes nothing returns nothing" + System.lineSeparator()
				+ "local integer a=5" + System.lineSeparator()
				+ "local integer b=6" + System.lineSeparator()
				+ "call foo()" + System.lineSeparator()
				+ "call foo()" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void compoundOperator() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "local integer i\n"
				+ "set i/=5\n"
				+ "set i*=6\n"
				+ "set i-=6\n"
				+ "set i+=6\n"
				+ "local integer array bar\n"
				+ "set bar[1+3]+=4\n"
				+ "endfunction";

		String result = "function foo takes nothing returns nothing" + System.lineSeparator()
				+ "local integer i" + System.lineSeparator()
				+ "local integer array bar" + System.lineSeparator()
				+ "set i=i/5" + System.lineSeparator()
				+ "set i=i*6" + System.lineSeparator()
				+ "set i=i-6" + System.lineSeparator()
				+ "set i=i+6" + System.lineSeparator()
				+ "set bar[1+3]=bar[1+3]+4" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

}
