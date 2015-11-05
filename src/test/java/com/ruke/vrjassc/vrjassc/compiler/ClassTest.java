package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class ClassTest extends TestHelper {

	@Test
	public void test() {
		String code =
				"struct foo\n"
					+ "private integer bar\n"
					+ "private foo that\n"
					+ "private method baz takes nothing returns nothing\n"
						+ "set this.that.bar = 1\n"
						+ "call this.baz()\n"
					+ "endmethod\n"
				+ "endstruct";
		
		String expected =
				"globals\n"
					+ "hashtable vrjass_structs=InitHashtable()\n"
					+ "integer struct_foo_bar=1\n"
					+ "integer struct_foo_that=2\n"
				+ "endglobals\n"
				+ "function struct_foo_baz takes integer this returns nothing\n"
					+ "call SaveInteger(vrjass_structs,LoadInteger(vrjass_structs,this,struct_foo_that),struct_foo_bar,1)\n"
					+ "call struct_foo_baz(this)\n"
				+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
