package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.NoScopeVisibilityException;
import com.ruke.vrjassc.vrjassc.util.Compile;

public class FunctionDefinitionTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n\n"
				+ "endfunction";

		assertEquals(code, compile.run(code));
	}

	@Test
	public void privateOnNoScope() {
		Compile compile = new Compile();
		String code = "private function foo takes nothing returns nothing\n\n"
				+ "endfunction";

		expectedEx.expect(NoScopeVisibilityException.class);
		expectedEx
				.expectMessage("1:17 Element <foo> must be inside of an scope to declare visibility");

		compile.run(code);
	}

	@Test
	public void publicOnNoScope() {
		Compile compile = new Compile();
		String code = "public function foo takes nothing returns nothing\n\n"
				+ "endfunction";

		expectedEx.expect(NoScopeVisibilityException.class);
		expectedEx
				.expectMessage("1:16 Element <foo> must be inside of an scope to declare visibility");

		compile.run(code);
	}

}
