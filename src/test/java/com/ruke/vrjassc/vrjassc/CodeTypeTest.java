package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.Compile;

public class CodeTypeTest {

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code = "function foo takes code func returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function bar takes nothing returns nothing" + System.lineSeparator()
				+ "local code f=function bar" + System.lineSeparator()
				+ "call foo(f)" + System.lineSeparator()
				+ "endfunction";

		assertEquals(code, compile.run(code));
	}

}
