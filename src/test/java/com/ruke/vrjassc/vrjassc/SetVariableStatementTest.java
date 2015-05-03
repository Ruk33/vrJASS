package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.UndefinedVariableException;
import com.ruke.vrjassc.vrjassc.util.Compile;

public class SetVariableStatementTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void setVariable() {
		Compile compile = new Compile();
		String code = "function foo takes integer bar returns integer\n"
				+ "set bar=2\n" + "return 1\n" + "endfunction";

		assertEquals(code, compile.run(code));
	}

	@Test
	public void setUndefinedVariable() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "set bar=2\n" + "endfunction";

		expectedEx.expect(UndefinedVariableException.class);
		expectedEx.expectMessage("2:4 Variable <bar> is not defined");

		compile.run(code);
	}

}
