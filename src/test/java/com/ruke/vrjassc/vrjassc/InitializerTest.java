package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.Compile;

public class InitializerTest {

	@Test
	public void test() {
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

}
