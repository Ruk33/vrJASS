package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.UndefinedModuleException;
import com.ruke.vrjassc.vrjassc.util.Compile;

public class ModuleTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void test() {
		Compile compile = new Compile();
		String code = "library Bar\n"
				+ "public struct Person extends array\n"
				+ "implements Foo\n"
				+ "endstruct\n"
				+ "public module Foo\n"
				+ "public static method lorem takes nothing returns integer\n"
				+ "return 1\n"
				+ "endmethod\n"
				+ "private method ipsum takes integer i returns nothing\n"
				+ "endmethod\n"
				+ "endmodule\n"
				+ "endlibrary";

		String result = "function struct_s_Bar_Person_lorem takes nothing returns integer" + System.lineSeparator()
				+ "return 1" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_Bar_Person__ipsum takes integer this,integer i returns nothing" + System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void undefined() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n"
				+ "implement Bar\n"
				+ "endstruct";

		expectedEx.expect(UndefinedModuleException.class);
		expectedEx.expectMessage("2:10 Module <Bar> is not defined");

		compile.run(code);
	}

}
