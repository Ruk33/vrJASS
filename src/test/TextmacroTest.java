package test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import util.Compile;

public class TextmacroTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void correct() {
		Compile compile = new Compile();
		String code =
				"//! textmacro foo takes bar\n"
				+ "function lorem_$bar$ takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "//! endtextmacro\n"
				+ "//! runtextmacro foo(ipsum)\n"
				+ "//! runtextmacro foo(noname)";
		
		String result =
				"function lorem_ipsum takes nothing returns nothing\n\n"
				+ "endfunction\n"
				+ "function lorem_noname takes nothing returns nothing\n\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}

}
