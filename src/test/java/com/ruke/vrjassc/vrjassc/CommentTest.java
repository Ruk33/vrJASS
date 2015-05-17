package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.Compile;

public class CommentTest {

	@Test
	public void test() {
		Compile compile = new Compile();
		String code = "/**\n"
				+ "something\n"
				+ "*/\n"
				+ "function foo takes nothing returns nothing\n"
				+ "endfunction";
		
		String result = "function foo takes nothing returns nothing" + System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}

}
