package com.ruke.vrjassc.vrjassc.compiler;

import com.ruke.vrjassc.Config;
import com.ruke.vrjassc.vrjassc.util.TestHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionTest extends TestHelper {

	@Test
	public void continueStatement() {
		String code =
			"function foo\n" +
				"local integer i\n" +
				"set i = 1\n" +
				"loop\n" +
					"exitwhen i > 9\n" +
					"if i == 3 then\n" +
						"continue\n" +
					"endif\n" +
					"if i == 5 then\n" +
						"continue\n" +
					"endif\n" +
					"call BJDebugMsg(I2S(i))\n" +
					"set i = i + 1\n" +
				"endloop\n" +
			"end";

		String expected =
			"globals\n" +
			"endglobals\n" +
			"function foo takes nothing returns nothing\n" +
				"boolean vr_c_0=false\n" +
				"local integer i=0\n" +
				"set i=1\n" +
				"loop\n" +
					"set vr_c_0=false\n" +
					"loop\n" +
						"exitwhen i>9\n" +
						"if i==3 then\n" +
							"set vr_c_0=true\n" +
							"exitwhen true\n" +
						"endif\n" +
						"if i==5 then\n" +
							"set vr_c_0=true\n" +
							"exitwhen true\n" +
						"endif\n" +
						"call BJDebugMsg(I2S(i))\n" +
						"set i=i+1\n" +
					"endloop\n" +
					"exitwhen vr_c_0==false\n" +
				"endloop\n" +
			"endfunction";

		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void anonymousFunction() {
		String code =
			"function foo\n"
				+ "local integer i=0\n"
				+ "local code bar = function\n"
					+ "local integer baz = 1\n"
				+ "endfunction\n"
				+ "call TimerStart(CreateTimer(), 2, true, function\n"
					+ "local integer i=1\n"
				+ "endfunction)\n"
				+ "call TimerStart(CreateTimer(), 3, true, bar)\n"
			+ "endfunction";
		
		String expected =
			("globals\n"
			+ "endglobals\n"
			+ "function %s_1 takes nothing returns nothing\n"
				+ "local integer baz=1\n"
			+ "endfunction\n"
			+ "function %s_2 takes nothing returns nothing\n"
				+ "local integer i=1\n"
			+ "endfunction\n"
			+ "function foo takes nothing returns nothing\n"
				+ "local integer i=0\n"
				+ "local code bar=function %s_1\n"
				+ "call TimerStart(CreateTimer(),2,true,function %s_2)\n"
				+ "call TimerStart(CreateTimer(),3,true,bar)\n"
			+ "endfunction").replaceAll("%s", Config.ANONYMOUS_FUNCTIONS_PREFIX);
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void breakStatement() {
		String code =
			"function foo\n"
				+ "while false\n"
					+ "break\n"
				+ "endwhile\n"
			+ "end";
		
		String expected =
			"globals\n"
			+ "endglobals\n"
			+ "function foo takes nothing returns nothing\n"
				+ "loop\n"
					+ "exitwhen not (false)\n"
					+ "exitwhen true\n"
				+ "endloop\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void whileLoop() {
		String code =
			"function foo\n"
				+ "while true\n"
					+ "call foo()\n"
				+ "endwhile\n"
				+ "local integer i\n"
				+ "while (i)\n"
				+ "endwhile\n"
			+ "end";
		
		String expected =
			"globals\n"
			+ "endglobals\n"
			+ "function foo takes nothing returns nothing\n"
				+ "local integer i=0\n"
				+ "loop\n"
					+ "exitwhen not (true)\n"
					+ "call foo()\n"
				+ "endloop\n"
				+ "loop\n"
					+ "exitwhen not ((i)!=0)\n"
				+ "endloop\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void end() {
		String code =
			"function foo\n"
				+ "if false then\n"
				+ "else\n"
				+ "end\n"
			+ "end";
		
		String expected =
			"globals\n"
			+ "endglobals\n"
			+ "function foo takes nothing returns nothing\n"
				+ "if false then\n"
				+ "else\n"
				+ "endif\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void not() {
		String code =
			"function foo\n"
				+ "local boolean f = not false\n"
				+ "local integer i\n"
				+ "local boolean e = not i\n"
			+ "endfunction";
		
		String expected =
			"globals\n"
			+ "endglobals\n"
			+ "function foo takes nothing returns nothing\n"
				+ "local boolean f=not (false)\n"
				+ "local integer i=0\n"
				+ "local boolean e=not (i!=0)\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
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
			"        return cast this to integer == cast e to integer\n" +
			"    endmethod\n" + 
			"endstruct";
			
		String expected =
			"globals\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
				+ "integer vtype=-1\n"
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
				+ "integer vtype=-1\n"
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
