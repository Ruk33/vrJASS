package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.InvalidBooleanException;
import com.ruke.vrjassc.vrjassc.util.Compile;

public class IfStatementTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "if (true and false or (false and true)) then\n"
				+ "call foo()\n" + "call foo()\n" + "elseif (true) then\n"
				+ "call foo()\n" + "else\n" + "call foo()\n" + "endif\n"
				+ "if true then\n" + "endif\n" + "if false then\n" + "else\n"
				+ "endif\n" + "endfunction";

		assertEquals(code, compile.run(code));
	}

	@Test
	public void invalidIfExpression() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "if (foo()) then\n" + "endif\n" + "endfunction";

		expectedEx.expect(InvalidBooleanException.class);
		expectedEx
				.expectMessage("2:3 Expression type must be <boolean>, but <nothing> type given");

		compile.run(code);
	}

	@Test
	public void invalidElseIfExpression() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "if (true) then\n" + "elseif (foo()) then\n" + "endif\n"
				+ "endfunction";

		expectedEx.expect(InvalidBooleanException.class);
		expectedEx
				.expectMessage("3:7 Expression type must be <boolean>, but <nothing> type given");

		compile.run(code);
	}

	@Test
	public void integer() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "if 0 then\n" + "elseif 1 then\n" + "elseif 1 or 1 then\n"
				+ "elseif 1 and 1 then\n" + "endif\n" + "endfunction";

		String result = "function foo takes nothing returns nothing\n"
				+ "if 0!=0 then\n" + "elseif 1!=0 then\n"
				+ "elseif 1!=0 or 1!=0 then\n" + "elseif 1!=0 and 1!=0 then\n"
				+ "endif\n" + "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void handle() {
		Compile compile = new Compile();
		String code = "function foo takes unit whichUnit returns nothing\n"
				+ "if null then\n" + "elseif whichUnit then\n" + "endif\n"
				+ "endfunction";

		String result = "function foo takes unit whichUnit returns nothing\n"
				+ "if null!=null then\n" + "elseif whichUnit!=null then\n"
				+ "endif\n" + "endfunction";

		assertEquals(result, compile.run(code));
	}

}
