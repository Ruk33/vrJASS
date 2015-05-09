package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.NoFunctionException;
import com.ruke.vrjassc.vrjassc.util.Compile;

public class FunctionCallTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing" + System.lineSeparator()
				+ "call foo()" + System.lineSeparator()
				+ "endfunction";

		assertEquals(code, compile.run(code));
	}

	/*
	 * @Test public void callPrivateUsingPrefix() { Compile compile = new
	 * Compile(); String code = "library foo\n" +
	 * "private function nope takes nothing returns nothing\n" + "endfunction\n"
	 * + "endlibrary\n"
	 *
	 * + "function bar takes nothing returns nothing\n" + "call foo__nope()\n" +
	 * "endfunction";
	 *
	 * expectedEx.expect(ElementNoAccessException.class);
	 * expectedEx.expectMessage( "6:5 No access to element <foo__nope>" );
	 *
	 * compile.run(code); }
	 */
	@Test
	public void callPublic() {
		Compile compile = new Compile();
		String code = "library foo\n"
				+ "public function yep takes nothing returns nothing\n"
				+ "endfunction\n" + "endlibrary\n"
				+ "function bar takes nothing returns nothing\n"
				+ "call foo_yep()\n" + "endfunction";

		String result = "function foo_yep takes nothing returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function bar takes nothing returns nothing" + System.lineSeparator()
				+ "call foo_yep()" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void publicAutoPrefixFromInsideOfScope() {
		Compile compile = new Compile();
		String code = "library foo\n"
				+ "public function bar takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "public function yep takes nothing returns nothing\n"
				+ "call bar()\n"
				+ "call foo_bar()\n"
				+ "endfunction\n"
				+ "endlibrary\n";

		String result = "function foo_bar takes nothing returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function foo_yep takes nothing returns nothing" + System.lineSeparator()
				+ "call foo_bar()" + System.lineSeparator()
				+ "call foo_bar()" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void privateAutoPrefixFromInsideOfScope() {
		Compile compile = new Compile();
		String code = "library foo\n"
				+ "private function bar takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "public function yep takes nothing returns nothing\n"
				+ "call bar()\n"
				+ "call foo__bar()\n"
				+ "endfunction\n"
				+ "endlibrary\n";

		String result = "function foo__bar takes nothing returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function foo_yep takes nothing returns nothing" + System.lineSeparator()
				+ "call foo__bar()" + System.lineSeparator()
				+ "call foo__bar()" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void functionSort() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "call bar()\n"
				+ "endfunction\n"
				+ "function lorem takes nothing returns nothing\n"
				+ "call foo()\n"
				+ "endfunction\n"
				+ "function bar takes nothing returns nothing\n\n"
				+ "endfunction";

		String result = "function bar takes nothing returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function foo takes nothing returns nothing" + System.lineSeparator()
				+ "call bar()" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function lorem takes nothing returns nothing" + System.lineSeparator()
				+ "call foo()" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void functionAlternativeSort() {
		Compile compile = new Compile();
		String code = "function foo takes integer i returns integer\n"
				+ "return bar(i)\n"
				+ "endfunction\n"
				+ "function bar takes integer i returns integer\n"
				+ "return foo(i)\n"
				+ "endfunction";

		String result = "globals" + System.lineSeparator()
				+ "integer vrjass_c_foo_i" + System.lineSeparator()
				+ "integer vrjass_c_foo_return" + System.lineSeparator()
				+ "endglobals" + System.lineSeparator()
				+ "function vrjass_c_foo takes integer i returns integer" + System.lineSeparator()
				+ "set vrjass_c_foo_i=i" + System.lineSeparator()
				+ "call ExecuteFunc(\"vrjass_c_noargs_foo\")" + System.lineSeparator()
				+ "return vrjass_c_foo_return" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function bar takes integer i returns integer" + System.lineSeparator()
				+ "return vrjass_c_foo(i)" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function foo takes integer i returns integer" + System.lineSeparator()
				+ "return bar(i)" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function vrjass_c_noargs_foo takes nothing returns nothing" + System.lineSeparator()
				+ "set vrjass_c_foo_return=foo(vrjass_c_foo_i)" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void callNoFunction() {
		Compile compile = new Compile();
		String code = "function foo takes integer i returns nothing\n"
				+ "call i\n" + "endfunction";

		expectedEx.expect(NoFunctionException.class);
		expectedEx.expectMessage("2:5 Element <i> is not a function");

		compile.run(code);
	}

}
