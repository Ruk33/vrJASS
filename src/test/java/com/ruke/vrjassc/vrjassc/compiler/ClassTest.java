package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class ClassTest extends TestHelper {
	
	@Test
	public void types() {
		String code =
			"struct foo\n"
				+ "public player p\n"
				+ "method bar\n"
					+ "set this.p=this.p\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n" +
				"integer struct_foo_p=1\n" +
				"hashtable vrjass_structs=InitHashtable()\n" +
			"endglobals\n" +
			"function struct_foo_bar takes integer this returns nothing\n" +
			"call SavePlayerHandle(vrjass_structs,this,struct_foo_p,LoadPlayerHandle(vrjass_structs,this,struct_foo_p))\n" +
			"endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void scopedTypes() {
		String code =
			"library foo\n"
				+ "public struct foo\n"
					+ "public method baz\n"
					+ "endmethod\n"
				+ "endstruct\n"
			+ "endlibrary\n"
			+ "struct bar\n"
				+ "foo.foo l\n"
				+ "method baz\n"
					+ "call this.l.baz()\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n"
				+ "integer struct_bar_l=1\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
			+ "endglobals\n"
			+ "function struct_foo_foo_baz takes integer this returns nothing\n"
			+ "endfunction\n"
			+ "function struct_bar_baz takes integer this returns nothing\n"
				+ "call struct_foo_foo_baz(LoadInteger(vrjass_structs,this,struct_bar_l))\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void scopes() {
		String code =
			"struct foo\n"
				+ "integer baz\n"
				+ "method bar takes foo i\n"
					+ "set i.baz=1\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n"
				+ "integer struct_foo_baz=1\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
			+ "endglobals\n"
			+ "function struct_foo_bar takes integer this,integer i returns nothing\n"
				+ "call SaveInteger(vrjass_structs,i,struct_foo_baz,1)\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void cast() {
		String code =
			"struct foo\n"
				+ "static integer instances\n"
				+ "static method allocate takes nothing returns foo\n"
					+ "set foo.instances += 1\n"
					+ "call 0 cast foo.bar()\n"
					+ "call (1 cast foo).bar()\n"
					+ "call BJDebugMsg(\"instance \" + I2S(foo.allocate() cast integer))\n"
					+ "return foo.instances cast foo\n"
				+ "endmethod\n"
				+ "method bar takes nothing returns nothing\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n"
				+ "integer struct_foo_instances=0\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
			+ "endglobals\n"
			+ "function struct_foo_bar takes integer this returns nothing\n"
			+ "endfunction\n"
			+ "function struct_foo_allocate takes nothing returns integer\n"
				+ "set struct_foo_instances=struct_foo_instances+1\n"
				+ "call struct_foo_bar(0)\n"
				+ "call struct_foo_bar((1))\n" // <-- the power
				+ "call BJDebugMsg(\"instance \"+I2S(struct_foo_allocate()))\n"
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
					+ "integer struct_foo_a=0\n"
					+ "integer struct_foo_b=0\n"
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
				+ "integer i\n"
				+ "method baz takes nothing returns nothing\n"
					+ "set this.bar[this.i] = 1\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n"
				+ "integer struct_foo_bar=1\n"
				+ "integer struct_foo_i=2\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
			+ "endglobals\n"
			+ "function struct_foo_baz takes integer this returns nothing\n"
				+ "call SaveInteger(vrjass_structs,this,struct_foo_bar*8191-IMinBJ(LoadInteger(vrjass_structs,this,struct_foo_i),8191),1)\n"
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
