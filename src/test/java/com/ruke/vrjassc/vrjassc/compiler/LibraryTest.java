package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class LibraryTest extends TestHelper {

	@Test
	public void usign() {
		this.run("public function bool takes boolean b, integer line\n" +
			     "   if (b == false) then\n" +
			     "       //local string expected = \"false\"\n" +
			     "       \n" +
			     "       //if (b) then\n" +
			     "       //    set expected = \"true\"\n" +
			     "       //endif\n" +
			            
			     "       //call print(\"[FAIL] Expected \" + \" on line \" + I2S(line))\n" +
			     "   endif\n" +
			     "endfunction\n" +
			
			     "public function toBeFalse takes boolean b, integer line\n" +
			     "    call bool(b == false, line)\n" +
			     "endfunction");
	}
	
	@Test
	public void test() {
		String code =
			"library foo\n"
				+ "public function bar\n"
				+ "endfunction\n"
				+ "public struct bee\n"
					+ "public static method baa\n"
					+ "endmethod\n"
				+ "endstruct\n"
			+ "endlibrary\n"
			+ "function baz\n"
				+ "call foo.bar()\n"
				+ "call foo.bee.baa()\n"
			+ "endfunction";
		
		String expected =
			"globals\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
			+ "endglobals\n"
			+ "function foo_bar takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "function struct_foo_bee_baa takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "function baz takes nothing returns nothing\n"
				+ "call foo_bar()\n"
				+ "call struct_foo_bee_baa()\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
