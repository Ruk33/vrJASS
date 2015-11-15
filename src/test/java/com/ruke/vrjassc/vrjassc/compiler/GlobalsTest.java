package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class GlobalsTest extends TestHelper {

	@Test
	public void test() {
		String code =
			"globals\n"
				+ "integer bar = 1\n"
			+ "endglobals\n"
			+ "function foo takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "globals\n"
				+ "integer baz\n"
			+ "endglobals";
		
		String expected =
			"globals\n"
				+ "integer bar=1\n"
				+ "integer baz=0\n"
			+ "endglobals\n"
			+ "function foo takes nothing returns nothing\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
