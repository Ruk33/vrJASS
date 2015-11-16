package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class OperatorTest extends TestHelper {
	
	@Test
	public void test() {
		String code =
			"function foo takes nothing returns nothing\n"
				+ "local integer i = 1\n"
				+ "set i += 1\n"
				+ "set i -= 1\n"
				+ "set i /= 1\n"
				+ "set i *= 1\n"
			+ "endfunction";
		
		String expected =
			"globals\n"
			+ "endglobals\n"
			+ "function foo takes nothing returns nothing\n"
				+ "local integer i=1\n"
				+ "set i=i+1\n"
				+ "set i=i-1\n"
				+ "set i=i/1\n"
				+ "set i=i*1\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
