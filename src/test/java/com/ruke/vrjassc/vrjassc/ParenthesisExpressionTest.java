package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.Compile;

public class ParenthesisExpressionTest {

	@Test
	public void test() {
		Compile compile = new Compile();
		String code = "function foo takes integer i returns integer" + System.lineSeparator()
				+ "return foo((4+i)+2)" + System.lineSeparator()
				+ "endfunction";

		assertEquals(code, compile.run(code));
	}

}
