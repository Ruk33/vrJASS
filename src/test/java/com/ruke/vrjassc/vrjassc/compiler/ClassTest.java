package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class ClassTest extends TestHelper {

	@Test
	public void cast() {
		String code =
			"struct foo\n"
				+ "static integer instances\n"
				+ "static method allocate takes nothing returns foo\n"
					+ "set foo.instances += 1\n"
					+ "return foo.instances cast foo\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n"
				+ "integer struct_foo_instances\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
			+ "endglobals\n"
			+ "function struct_foo_allocate takes nothing returns integer\n"
				+ "set struct_foo_instances=struct_foo_instances+1\n"
				+ "return struct_foo_instances\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void _extends() {
		String code =
			"struct foo\n"
				+ "public method baz takes nothing returns nothing\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "struct bar extends foo\n"
				+ "public method n takes nothing returns nothing\n"
					+ "call this.baz()\n"
					+ "call this.e()\n"
				+ "endmethod\n"
				+ "public method e takes nothing returns nothing\n"
				+ "endmethod\n"
				+ "public static method a takes nothing returns nothing\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
			+ "endglobals\n"
			+ "function struct_foo_baz takes integer this returns nothing\n"
			+ "endfunction\n"
			+ "function struct_foo_bar_e takes integer this returns nothing\n"
			+ "endfunction\n"
			+ "function struct_foo_bar_n takes integer this returns nothing\n"
				+ "call struct_foo_baz(this)\n"
				+ "call struct_foo_bar_e(this)\n"
			+ "endfunction\n"
			+ "function struct_foo_bar_a takes nothing returns nothing\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void test() {
		String code =
				"struct foo\n"
					+ "private static integer a=1\n"
					+ "private static integer b\n"
					+ "private integer bar\n"
					+ "private foo that\n"
					+ "private method baz takes nothing returns nothing\n"
						+ "set this.that.bar += 1\n"
						+ "call this.baz()\n"
					+ "endmethod\n"
				+ "endstruct";
		
		String expected =
				"globals\n"
					+ "integer struct_foo_a\n"
					+ "integer struct_foo_b\n"
					+ "integer struct_foo_bar=1\n"
					+ "integer struct_foo_that=2\n"
					+ "hashtable vrjass_structs=InitHashtable()\n"
				+ "endglobals\n"
				+ "function struct_foo_baz takes integer this returns nothing\n"
					+ "call SaveInteger("
						+ "vrjass_structs,"
						+ "LoadInteger("
							+ "vrjass_structs,"
							+ "this,"
							+ "struct_foo_that"
						+ "),"
						+ "struct_foo_bar,"
						+ "LoadInteger("
							+ "vrjass_structs,"
							+ "LoadInteger("
								+ "vrjass_structs,"
								+ "this,"
								+ "struct_foo_that"
							+ "),"
							+ "struct_foo_bar"
							+ ")+1"
						+ ")\n"
					+ "call struct_foo_baz(this)\n"
				+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void array() {
		String code =
			"struct foo\n"
				+ "integer array bar\n"
				+ "method baz takes nothing returns nothing\n"
					+ "set this.bar[2] = 1\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n"
				+ "integer struct_foo_bar=1\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
			+ "endglobals\n"
			+ "function struct_foo_baz takes integer this returns nothing\n"
				+ "call SaveInteger(vrjass_structs,this,struct_foo_bar*8191-IMinBJ(2,8191),1)\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void onInit() {
		String code =
			"struct foo\n"
				+ "static method onInit takes nothing returns nothing\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "function main takes nothing returns nothing\n"
			+ "endfunction";
		
		String expected =
			"globals\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
			+ "endglobals\n"
			+ "function struct_foo_onInit takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "function main takes nothing returns nothing\n"
				+ "call ExecuteFunc(\"struct_foo_onInit\")\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
