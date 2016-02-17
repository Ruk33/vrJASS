package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class LibraryTest extends TestHelper {
	
	@Test
	public void returnUsingNamespace() {
		String code =
			"library foo\n"
				+ "public struct bar\n"
				+ "endstruct\n"
			+ "endlibrary\n"
			+ "function lorem returns foo.bar\n"
				+ "return 0 cast foo.bar\n"
			+ "endfunction";
		
		String expected =
			"globals\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
				+ "integer vtype=-1\n"
			+ "endglobals\n"
			+ "function lorem takes nothing returns integer\n"
				+ "return 0\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	@Ignore
	public void test() {
		String code =
			"function baz\n"
				+ "call foo.bar()\n"
				+ "call foo.bee.baa()\n"
			+ "endfunction\n"
			+ "library foo\n"
				+ "public function bar\n"
				+ "endfunction\n"
				+ "public struct bee\n"
					+ "public static method baa\n"
					+ "endmethod\n"
				+ "endstruct\n"
			+ "endlibrary";
		
		String expected =
			"globals\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
				+ "integer vtype=-1\n"
			+ "endglobals\n"
			+ "function struct_foo_bee_baa takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "function foo_bar takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "function baz takes nothing returns nothing\n"
				+ "call foo_bar()\n"
				+ "call struct_foo_bee_baa()\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
