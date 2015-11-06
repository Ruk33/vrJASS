package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class InitializatorTest extends TestHelper {

	@Test
	public void test() {
		String code =
			"library foo initializer bar\n"
				+ "function bar takes nothing returns nothing\n"
				+ "endfunction\n"
			+ "endlibrary\n"
			+ "function main takes nothing returns nothing\n"
			+ "endfunction";
		
		String expected =
			"globals\n"
			+ "endglobals\n"
			+ "function foo_bar takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "function main takes nothing returns nothing\n"
				+ "call ExecuteFunc(\"foo_bar\")\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
