package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.MathematicalExpressionException;
import com.ruke.vrjassc.vrjassc.util.Compile;

public class MathExpressionTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code = "function foo takes integer a returns nothing" + System.lineSeparator()
				+ "set a=a*a*4" + System.lineSeparator()
				+ "set a=a/a/4" + System.lineSeparator()
				+ "set a=a-a-4" + System.lineSeparator()
				+ "set a=a+a+4" + System.lineSeparator()
				+ "set a=a*5/4-2+4" + System.lineSeparator()
				+ "endfunction";

		assertEquals(code, compile.run(code));
	}

	@Test
	public void wrongDiv() {
		Compile compile = new Compile();
		String code = "function foo takes integer a returns nothing\n"
				+ "set a=a/\"nope\"\n" + "endfunction";

		expectedEx.expect(MathematicalExpressionException.class);
		expectedEx
				.expectMessage("2:8 Incorrect mathematical expression (only integers and reals)");

		compile.run(code);
	}

	@Test
	public void wrongMult() {
		Compile compile = new Compile();
		String code = "function foo takes integer a returns nothing\n"
				+ "set a=a*\"nope\"\n" + "endfunction";

		expectedEx.expect(MathematicalExpressionException.class);
		expectedEx
				.expectMessage("2:8 Incorrect mathematical expression (only integers and reals)");

		compile.run(code);
	}

	@Test
	public void wrongMinus() {
		Compile compile = new Compile();
		String code = "function foo takes integer a returns nothing\n"
				+ "set a=a-\"nope\"\n" + "endfunction";

		expectedEx.expect(MathematicalExpressionException.class);
		expectedEx
				.expectMessage("2:8 Incorrect mathematical expression (only integers and reals)");

		compile.run(code);
	}

	@Test
	public void wrongPlus() {
		Compile compile = new Compile();
		String code = "function foo takes integer a returns nothing\n"
				+ "set a=a+\"nope\"\n" + "endfunction";

		expectedEx.expect(MathematicalExpressionException.class);
		expectedEx
				.expectMessage("2:8 Incorrect mathematical expression (only integers and reals)");

		compile.run(code);
	}

	@Test
	public void wrongUsingFunction() {
		Compile compile = new Compile();
		String code = "function foo takes integer a returns nothing\n"
				+ "set a=a+bar()\n" + "endfunction\n"
				+ "function bar takes nothing returns string\n"
				+ "return \"nope\"\n" + "endfunction";

		expectedEx.expect(MathematicalExpressionException.class);
		expectedEx
				.expectMessage("2:8 Incorrect mathematical expression (only integers and reals)");

		compile.run(code);
	}

}
