package test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.InvalidBooleanException;
import util.Compile;

public class IfStatementTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void correct() {
		Compile compile = new Compile();
		String code =
				"function foo takes nothing returns nothing\n"
				+ "if (true and false or (false and true)) then\n"
				+ "call foo()\n"
				+ "call foo()\n"
				+ "elseif (true) then\n"
				+ "call foo()\n"
				+ "else\n"
				+ "call foo()\n"
				+ "endif\n"
				+ "if (true) then\n"
				+ "endif\n"
				+ "if (false) then\n"
				+ "else\n"
				+ "endif\n"
				+ "endfunction";
		
		assertEquals(code, compile.run(code));
	}
	
	@Test
	public void invalidExpression() {
		Compile compile = new Compile();
		String code =
				"function foo takes nothing returns nothing\n"
				+ "if (foo()) then\n"
				+ "endif\n"
				+ "endfunction";
		
		expectedEx.expect(InvalidBooleanException.class);
		expectedEx.expectMessage(
			"2:4 Expression type must be <boolean>, but <nothing> type given"
		);
		
		compile.run(code);
	}

}
