package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.Compile;

public class ReturnStatementTest {

	@Test
	public void test() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns integer\n"
				+ "return 2\n" + "endfunction";

		assertEquals(code, compile.run(code));
	}

}
