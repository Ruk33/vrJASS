package com.ruke.vrjassc.vrjassc.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModuleTest extends com.ruke.vrjassc.vrjassc.util.TestHelper {

	@Test
	public void onInit() {
		String code =
			"struct bar\n"
				+ "static method onInit\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "struct lorem\n"
				+ "use foo\n"
				+ "static method onInit\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "module foo\n"
				+ "static method onInit\n"
				+ "endmethod\n"
			+ "endmodule\n"
			+ "function main\n"
			+ "endfunction";
		
		String expected =
			"globals\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
			+ "endglobals\n"
			+ "function struct_bar_onInit takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "function struct_lorem_onInit takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "function module_foo_onInit takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "function main takes nothing returns nothing\n"
				+ "call ExecuteFunc(\"struct_bar_onInit\")\n"
				+ "call ExecuteFunc(\"module_foo_onInit\")\n"
				+ "call ExecuteFunc(\"struct_lorem_onInit\")\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void test() {
		String code =
			"module foo\n"
				+ "real pi\n"
				+ "public method bar\n"
					+ "set this.pi=3.14\n"
				+ "endmethod\n"
			+ "endmodule\n"
			+ "struct baz\n"
				+ "use foo\n"
			+ "endstruct\n"
			+ "struct lorem\n"
				+ "use foo\n"
				+ "method ipsum\n"
					+ "call this.bar()\n"
				+ "endmethod\n"
			+ "endstruct\n";
		
		String expected =
			"globals\n"
				+ "integer module_foo_pi=1\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
			+ "endglobals\n"
			+ "function module_foo_bar takes integer this returns nothing\n"
				+ "call SaveReal(vrjass_structs,this,module_foo_pi,3.14)\n"
			+ "endfunction\n"
			+ "function struct_lorem_ipsum takes integer this returns nothing\n"
				+ "call module_foo_bar(this)\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
