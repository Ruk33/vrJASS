package test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.InvalidBooleanException;
import util.Compile;

public class LoopStatementTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void correct() {
		Compile compile = new Compile();
		String code =
				"function foo takes nothing returns nothing\n"
				+ "loop\n\n\n"
				+ "call foo()\n"
				+ "exitwhen(false)\n"
				+ "endloop\n"
				+ "endfunction";
		
		String result =
				"function foo takes nothing returns nothing\n"
				+ "loop\n"
				+ "call foo()\n"
				+ "exitwhen (false)\n"
				+ "endloop\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void invalidExitwhenExpression() {
		Compile compile = new Compile();
		String code =
				"function foo takes nothing returns nothing\n"
				+ "loop\n"
				+ "exitwhen(foo())\n"
				+ "endloop\n"
				+ "endfunction";
		
		expectedEx.expect(InvalidBooleanException.class);
		expectedEx.expectMessage(
			"3:9 Expression type must be <boolean>, but <nothing> type given"
		);
		
		compile.run(code);
	}

}
