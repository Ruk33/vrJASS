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
	
	@Test
	public void functionSort() {
		Compile compile = new Compile();
		String code =
				"function foo takes nothing returns nothing\n"
				+ "call bar()\n"
				+ "endfunction\n"
				+ "function lorem takes nothing returns nothing\n"
				+ "call foo()\n"
				+ "endfunction\n"
				+ "function bar takes nothing returns nothing\n\n"
				+ "endfunction";
		
		String result =
				"function bar takes nothing returns nothing\n\n"
				+ "endfunction\n"
				+ "function foo takes nothing returns nothing\n"
				+ "call bar()\n"
				+ "endfunction\n"
				+ "function lorem takes nothing returns nothing\n"
				+ "call foo()\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void functionAlternativeSort() {
		Compile compile = new Compile();
		String code =
				"function foo takes integer i returns integer\n"
				+ "return bar(i)\n"
				+ "endfunction\n"
				+ "function bar takes integer i returns integer\n"
				+ "return foo(i)\n"
				+ "endfunction";
		
		String result =
				"globals\n"
				+ "integer vrjass_c_foo_i\n"
				+ "integer vrjass_c_foo_return\n"
				+ "endglobals\n"
				+ "function vrjass_c_foo takes integer i returns integer\n"
				+ "set vrjass_c_foo_i=i\n"
				+ "call ExecuteFunc(\"vrjass_c_noargs_foo\")\n"
				+ "return vrjass_c_foo_return\n"
				+ "endfunction\n"
				+ "function bar takes integer i returns integer\n"
				+ "return vrjass_c_foo(i)\n"
				+ "endfunction\n"
				+ "function foo takes integer i returns integer\n"
				+ "return bar(i)\n"
				+ "endfunction\n"
				+ "function vrjass_c_noargs_foo takes nothing returns nothing\n"
				+ "set vrjass_c_foo_return=foo(vrjass_c_foo_i)\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}

}
