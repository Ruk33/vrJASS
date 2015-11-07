package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class FunctionTest extends TestHelper {
	
	@Test
	public void test() {
		String code =
				"function foo takes nothing returns nothing\n"
					+ "local string s\n"
					+ "if s then\n"
						+ "local real pi = 3.14\n"
						+ "loop\n"
							+ "call foo()\n"
							+ "exitwhen true\n"
						+ "endloop\n"
					+ "endif\n"
				+ "endfunction";
		
		String expected =
				"globals\n"
				+ "endglobals\n"
				+ "function foo takes nothing returns nothing\n"
					+ "local real pi\n"
					+ "local string s\n"
					+ "if StringLength(s)!=0 then\n"
						+ "set pi=3.14\n"
						+ "loop\n"
							+ "call foo()\n"
							+ "exitwhen true\n"
						+ "endloop\n"
					+ "endif\n"
				+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
