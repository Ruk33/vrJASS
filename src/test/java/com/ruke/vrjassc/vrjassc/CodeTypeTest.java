package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.Compile;

public class CodeTypeTest {

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code = "function foo takes code func returns nothing\n\n"
				+ "endfunction\n"
				+ "function bar takes nothing returns nothing\n"
				+ "local code f=function bar\n" + "call foo(f)\n"
				+ "endfunction";

		assertEquals(code, compile.run(code));
	}

}
