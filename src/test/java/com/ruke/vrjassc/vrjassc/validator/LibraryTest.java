package com.ruke.vrjassc.vrjassc.validator;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.exception.AlreadyDefinedException;
import com.ruke.vrjassc.vrjassc.exception.NoAccessException;
import com.ruke.vrjassc.vrjassc.exception.UndefinedSymbolException;
import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class LibraryTest extends TestHelper {

	@Test
	public void callPrivateFunctionExternally() {
		this.expectedEx.expect(NoAccessException.class);
		this.expectedEx.expectMessage("7:9 Element <lorem> does not have access to element <lorem>");
		this.run("library foo\n"
					+ "private function lorem takes nothing returns nothing\n"
					+ "endfunction\n"
				+ "endlibrary\n"
				+ "library bar\n"
					+ "private function lorem takes nothing returns nothing\n"
						+ "call foo.lorem()\n"
					+ "endfunction\n"
				+ "endlibrary");
	}
	
	@Test
	public void callPublicFunctionExternally() {
		this.expectedEx.none();
		this.run("library foo\n"
					+ "public function lorem takes nothing returns nothing\n"
					+ "endfunction\n"
					+ "public function bar takes nothing returns nothing\n"
					+ "endfunction\n"
					+ "public function e takes nothing returns nothing\n"
					+ "endfunction\n"
				+ "endlibrary\n"
				+ "library bar\n"
					+ "private function lorem takes nothing returns nothing\n"
						+ "call foo.lorem()\n"
						+ "call foo.bar()\n"
						+ "call foo.e()\n"
					+ "endfunction\n"
				+ "endlibrary");
	}
	
	@Test
	public void functionsArePrivateByDefault() {
		this.expectedEx.expect(NoAccessException.class);
		this.expectedEx.expectMessage("7:9 Element <ipsum> does not have access to element <lorem>");
		this.run("library foo\n"
					+ "function lorem takes nothing returns nothing\n"
					+ "endfunction\n"
				+ "endlibrary\n"
				+ "library bar\n"
					+ "private function ipsum takes nothing returns nothing\n"
						+ "call foo.lorem()\n"
					+ "endfunction\n"
				+ "endlibrary");
	}
	
	@Test
	public void accessingPrivateGlobal() {
		this.expectedEx.expect(NoAccessException.class);
		this.expectedEx.expectMessage("8:20 Element <ipsum> does not have access to element <pi>");
		this.run("library foo\n"
					+ "globals\n"
						+ "private real pi = 3.14\n"
					+ "endglobals\n"
				+ "endlibrary\n"
				+ "library bar\n"
					+ "private function ipsum takes nothing returns nothing\n"
						+ "local real pi = foo.pi\n"
					+ "endfunction\n"
				+ "endlibrary");
	}
	
	@Test
	public void accessingPublicGlobal() {
		this.expectedEx.none();
		this.run("library foo\n"
					+ "globals\n"
						+ "public real pi = 3.14\n"
					+ "endglobals\n"
				+ "endlibrary\n"
				+ "library bar\n"
					+ "private function ipsum takes nothing returns nothing\n"
						+ "local real pi = foo.pi\n"
						+ "set foo.pi = pi*pi\n"
					+ "endfunction\n"
				+ "endlibrary");
	}
	
	/**
	 * I knnow, some languages allow it, actually jass does and local always wins
	 * But for some people this tend to confuse so I decided not to support it
	 */
	@Test
	public void dontAllowLocalAndGlobalVariablesShareName() {
		this.expectedEx.expect(AlreadyDefinedException.class);
		this.expectedEx.expectMessage("6:14 Element <pi> is already defined on 3:12");
		this.run("library foo\n"
					+ "globals\n"
						+ "public real pi\n"
					+ "endglobals\n"
					+ "function bar takes nothing returns nothing\n"
						+ "local integer pi\n"
					+ "endfunction\n"
				+ "endlibrary");
	}
	
	@Test
	public void requirements() {
		this.expectedEx.expect(UndefinedSymbolException.class);
		this.expectedEx.expectMessage("1:21 Element <bar> is not defined");
		this.run("library foo requires bar\n"
				+ "endlibrary");
	}
	
	@Test
	public void initializer() {
		this.expectedEx.expect(UndefinedSymbolException.class);
		this.expectedEx.expectMessage("1:24 Element <bar> is not defined");
		this.run("library foo initializer bar\n"
				+ "endlibrary");
	}

}
