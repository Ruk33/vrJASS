package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.Compile;

public class ReturnStatementTest {

	@Test
	public void test() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns integer" + System.lineSeparator()
				+ "return 2" + System.lineSeparator()
				+ "endfunction";

		assertEquals(code, compile.run(code));
	}

}
