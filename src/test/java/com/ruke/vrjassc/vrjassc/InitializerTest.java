package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.Compile;

public class InitializerTest {

	@Test
	public void library() {
		Compile compile = new Compile();
		String code = "library bar initializer init requires foo\n"
				+ "private function init takes nothing returns nothing\n\n"
				+ "endfunction\n"
				+ "endlibrary\n"
				+ "library foo initializer init\n"
				+ "private function init takes nothing returns nothing\n\n"
				+ "endfunction\n"
				+ "endlibrary\n"
				+ "function main takes nothing returns nothing\n\n"
				+ "endfunction";
		
		String result = "function bar__init takes nothing returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function foo__init takes nothing returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function main takes nothing returns nothing" + System.lineSeparator()
				+ System.lineSeparator()
				+ "call foo__init()" + System.lineSeparator()
				+ "call bar__init()" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void _class() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n"
				+ "private static method onInit takes nothing returns nothing\n"
				+ "endmethod\n"
				+ "endstruct\n"
				+ "function main takes nothing returns nothing\n\n"
				+ "endfunction";

		
		String result = "function struct_s_Foo__onInit takes nothing returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function main takes nothing returns nothing" + System.lineSeparator()
				+ System.lineSeparator()
				+ "call struct_s_Foo__onInit()" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void _module() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n"
				+ "implement Bar\n"
				+ "endstruct\n"
				+ "module Bar\n"
				+ "private static method onInit takes nothing returns nothing\n"
				+ "endmethod\n"
				+ "endmodule\n"
				+ "function main takes nothing returns nothing\n\n"
				+ "endfunction";

		
		String result = "function struct_s_Foo__onInit takes nothing returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function main takes nothing returns nothing" + System.lineSeparator()
				+ System.lineSeparator()
				+ "call struct_s_Foo__onInit()" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void vJassInitializeModule() {
		Compile compile = new Compile();
		String code = "library Foo requires Bar\n"
				
				+ "struct InitStruct extends array\n"
				+ "implement InitMod\n"
				+ "endstruct\n"
				
				+ "public struct InitStructTwo extends array\n"
				+ "implement InitMod\n"
				+ "endstruct\n"
				
				+ "private module InitMod\n"
				+ "private static method onInit takes nothing returns nothing\n"
				+ "endmethod\n"
				+ "endmodule\n"
				
				+ "endlibrary\n"

				
				+ "library Bar initializer Initialize\n"
				+ "private function Initialize takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "endlibrary\n"
				
				
				+ "function main takes nothing returns nothing\n\n"
				+ "endfunction";

		
		String result = "function struct_s_InitStruct__onInit takes nothing returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				
				+ "function struct_s_Foo_InitStructTwo__onInit takes nothing returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				
				+ "function Bar__Initialize takes nothing returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function main takes nothing returns nothing" + System.lineSeparator()
				+ System.lineSeparator()
				+ "call Bar__Initialize()" + System.lineSeparator()
				+ "call struct_s_InitStruct__onInit()" + System.lineSeparator()
				+ "call struct_s_Foo_InitStructTwo__onInit()" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

}
