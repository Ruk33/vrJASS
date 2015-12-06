package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class FunctionTest extends TestHelper {
	
	@Test
	public void code() {
		String code =
			"function foo\n"
				+ "local code f = function foo\n"
			+ "endfunction";
		
		String expected =
			"globals\n"
			+ "endglobals\n"
			+ "function foo takes nothing returns nothing\n"
				+ "local code f=function foo\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void sort() {
		String code =
			"function foo\n"
				+ "call assert()\n"
			+ "endfunction\n"
			+ "function assert\n"
				+ "call bar()\n"
			+ "endfunction\n"
			+ "function bar\n"
				+ ""
			+ "endfunction";
		
		String expected =
			"globals\n"
			+ "endglobals\n"
			+ "function bar takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "function assert takes nothing returns nothing\n"
				+ "call bar()\n"
			+ "endfunction\n"
			+ "function foo takes nothing returns nothing\n"
				+ "call assert()\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void ifElseIfElse() {
		String code =
				"function foo\n"
					+ "local integer e\n"
					+ "if false then\n"
						+ "set e = 0\n"
					+ "elseif true then\n"
						+ "set e = 1\n"
					+ "else\n"
						+ "set e = 2\n"
					+ "endif\n"
				+ "endfunction";
				
			String expected =
				"globals\n"
				+ "endglobals\n"
				+ "function foo takes nothing returns nothing\n"
					+ "local integer e=0\n"
					+ "if false then\n"
						+ "set e=0\n"
					+ "elseif true then\n"
						+ "set e=1\n"
					+ "else\n"
						+ "set e=2\n"
					+ "endif\n"
				+ "endfunction";
			
			assertEquals(expected, this.run(code));
	}
	
	@Test
	public void _this() {
		String code =
			"struct foo\n" + 
			"    public method equals takes foo e returns boolean\n" + 
			"        return this cast integer == e cast integer\n" + 
			"    endmethod\n" + 
			"endstruct";
			
		String expected =
			"globals\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
			+ "endglobals\n"
			+ "function struct_foo_equals takes integer this,integer e returns boolean\n"
				+ "return this==e\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void noParametersNoReturnNoNothing() {
		String code =
			"function foo returns nothing\n"
			+ "endfunction\n"
			+ "function bar takes integer a\n"
			+ "endfunction\n"
			+ "function baz\n"
			+ "endfunction";
		
		String expected =
			"globals\n"
			+ "endglobals\n"
			+ "function foo takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "function bar takes integer a returns nothing\n"
				+ "endfunction\n"
				+ "function baz takes nothing returns nothing\n"
				+ "endfunction";
	
		assertEquals(expected, this.run(code));
	}
	
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
							+ "exitwhen \"\"\n"
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
							+ "exitwhen StringLength(\"\")!=0\n"
							+ "exitwhen true\n"
						+ "endloop\n"
					+ "endif\n"
				+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
