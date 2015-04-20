package test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.ElementNoAccessException;
import util.Compile;

public class FunctionCallTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void correct() {
		Compile compile = new Compile();
		String code =
				"function foo takes nothing returns nothing\n" +
					"call foo()\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}
	
	@Test
	public void callPrivateUsingPrefix() {
		Compile compile = new Compile();
		String code =
				"library foo\n"
				+ "private function nope takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "endlibrary\n"
				
				+ "function bar takes nothing returns nothing\n"
				+ "call foo__nope()\n"
				+ "endfunction";
		
		expectedEx.expect(ElementNoAccessException.class);
		expectedEx.expectMessage(
			"6:5 No access to element <foo__nope>"
		);
		
		compile.run(code);
	}
	
	@Test
	public void callPublic() {
		Compile compile = new Compile();
		String code =
				"library foo\n"
				+ "public function yep takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "endlibrary\n"
				+ "function bar takes nothing returns nothing\n"
				+ "call foo_yep()\n"
				+ "endfunction";
		
		String result =
				"function foo_yep takes nothing returns nothing\n\n"
				+ "endfunction\n"
				+ "function bar takes nothing returns nothing\n"
				+ "call foo_yep()\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void publicAutoPrefixFromInsideOfScope() {
		Compile compile = new Compile();
		String code =
				"library foo\n"
				+ "public function bar takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "public function yep takes nothing returns nothing\n"
				+ "call bar()\n"
				+ "call foo_bar()\n"
				+ "endfunction\n"
				+ "endlibrary\n";
		
		String result =
				"function foo_bar takes nothing returns nothing\n\n"
				+ "endfunction\n"
				+ "function foo_yep takes nothing returns nothing\n"
				+ "call foo_bar()\n"
				+ "call foo_bar()\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void privateAutoPrefixFromInsideOfScope() {
		Compile compile = new Compile();
		String code =
				"library foo\n"
				+ "private function bar takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "public function yep takes nothing returns nothing\n"
				+ "call bar()\n"
				+ "call foo__bar()\n"
				+ "endfunction\n"
				+ "endlibrary\n";
		
		String result =
				"function foo__bar takes nothing returns nothing\n\n"
				+ "endfunction\n"
				+ "function foo_yep takes nothing returns nothing\n"
				+ "call foo__bar()\n"
				+ "call foo__bar()\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}

}
