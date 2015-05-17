package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.Compile;

public class ModuleTest {

	@Test
	public void test() {
		Compile compile = new Compile();
		String code = "struct Person extends array\n"
				+ "implements Foo\n"
				+ "endstruct\n"
				+ "module Foo\n"
				+ "public static method lorem takes nothing returns integer\n"
				+ "return 1\n"
				+ "endmethod\n"
				+ "private method ipsum takes integer i returns nothing\n"
				+ "endmethod\n"
				+ "endmodule";

		String result = "function struct_s_Person_lorem takes nothing returns integer" + System.lineSeparator()
				+ "return 1" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_Person__ipsum takes integer this,integer i returns nothing" + System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

}
