package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.InvalidBooleanException;
import com.ruke.vrjassc.vrjassc.util.Compile;

public class LoopStatementTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "loop\n\n\n"
				+ "call foo()\n"
				+ "exitwhen(false)\n"
				+ "endloop\n"
				+ "endfunction";

		String result = "function foo takes nothing returns nothing" + System.lineSeparator()
				+ "loop" + System.lineSeparator()
				+ "call foo()" + System.lineSeparator()
				+ "exitwhen (false)" + System.lineSeparator()
				+ "endloop" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void invalidExitwhenExpression() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n" + "loop\n"
				+ "exitwhen(foo())\n" + "endloop\n" + "endfunction";

		expectedEx.expect(InvalidBooleanException.class);
		expectedEx
				.expectMessage("3:8 Expression type must be <boolean>, but <nothing> type given");

		compile.run(code);
	}

}
