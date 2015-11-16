package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class BooleanTest extends TestHelper {

	@Test
	public void test() {
		String code = 
			"function foo takes nothing returns nothing\n"
				+ "local unit u=null\n"
				+ "local string s=\"\"\n"
				+ "if u then\n"
				+ "endif\n"
				+ "loop\n"
					+ "exitwhen u\n"
					+ "exitwhen s\n"
				+ "endloop\n"
			+ "endfunction";
		
		String expected = 
			"globals\n"
			+ "endglobals\n"
			+ "function foo takes nothing returns nothing\n"
				+ "local unit u=null\n"
				+ "local string s=\"\"\n"
				+ "if u!=null then\n"
				+ "endif\n"
				+ "loop\n"
					+ "exitwhen u!=null\n"
					+ "exitwhen StringLength(s)!=0\n"
				+ "endloop\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
