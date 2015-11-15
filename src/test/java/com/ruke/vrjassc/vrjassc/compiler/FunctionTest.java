package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class FunctionTest extends TestHelper {
	
	@Test
	public void autoInitializeVariables() {
		String code =
			"globals\n"
				+ "handle f\n"
			+ "endglobals\n"
			+ "struct bar\n"
			+ "endstruct\n"
			+ "function foo takes nothing returns nothing\n"
				+ "local integer a\n"
				+ "local real b\n"
				+ "local unit c\n"
				+ "local string d\n"
				+ "local boolean e\n"
				+ "local bar g\n"
			+ "endfunction";
		
		String expected =
			"globals\n"
				+ "handle f=null\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
			+ "endglobals\n"
			+ "function foo takes nothing returns nothing\n"
					+ "local integer a=0\n"
					+ "local real b=0.0\n"
					+ "local unit c=null\n"
					+ "local string d=\"\"\n"
					+ "local boolean e=false\n"
					+ "local integer g=0\n"
				+ "endfunction";
	
		assertEquals(expected, this.run(code));
	}
	
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
					+ "local real pi=0.0\n"
					+ "local string s=\"\"\n"
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
