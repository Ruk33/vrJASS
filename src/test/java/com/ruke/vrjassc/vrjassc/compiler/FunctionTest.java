package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class FunctionTest extends TestHelper {
	
	@Test
	public void f() {
		String code =
			"struct Object\n" +
			"    private static integer instances\n" +
			"    \n" +
			"    public static method allocate takes nothing returns Object\n" +
			"        set Object.instances += 1\n" +
			"        return Object.instances cast Object\n" +
			"    endmethod\n" +
			"endstruct\n" +
			"\n" +
			"struct Stack extends Object\n" +
			"    Object array elements\n" +
			"    integer size\n" +
			"    \n" +
			"    public method push takes Object e\n" +
			"        set this.size += 1\n" +
			"        set this.elements[this.size] = e\n" +
			"    endmethod\n" +
			"    \n" +
			"    public method pop\n" +
			"        set this.size = IMinBJ(0, this.size - 1)\n" +
			"    endmethod\n" +
			"    \n" +
			"    public method getSize returns integer\n" +
			"        return this.size\n" +
			"    endmethod\n" +
			"    \n" +
			"    static method onInit\n" +
			"        local Object a = Object.allocate()\n" +
			"        local Object b = Object.allocate()\n" +
			"        local Object c = Object.allocate()\n" +
			"        local Object d = Object.allocate()\n" +
			"        local Stack  s = Stack.allocate() cast Stack\n" +
			"        \n" +
			"        call s.push(a)\n" +
			"        call s.push(b)\n" +
			"        \n" +
			"        call BJDebugMsg(I2S(s.getSize()))\n" +
			"    endmethod\n" +
			"endstruct";
			
		this.run(code);
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
