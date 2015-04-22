package test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.IncorrectArgumentsTextmacroException;
import exception.UndefinedTextmacroException;
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
	
	@Test
	public void undefined() {
		Compile compile = new Compile();
		String code = "//! runtextmacro foo(ipsum)";
		
		expectedEx.expect(UndefinedTextmacroException.class);
		expectedEx.expectMessage("Textmacro <foo> is not defined");
		
		compile.run(code);
	}
	
	@Test
	public void incorrectAmountOfArguments() {
		Compile compile = new Compile();
		String code = "//! runtextmacro foo(lorem,ipsum,dolor,sit,amet)\n"
				+ "//! textmacro foo takes bar\n"
				+ "//! endtextmacro";
		
		expectedEx.expect(IncorrectArgumentsTextmacroException.class);
		expectedEx.expectMessage(
			"Incorrect amount of arguments passed to textmacro <foo>"
		);
		
		compile.run(code);
	}

}
