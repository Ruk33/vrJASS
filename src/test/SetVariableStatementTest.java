package test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.UndefinedVariableException;
import util.Compile;

public class SetVariableStatementTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void setVariable() {
		Compile compile = new Compile();
		String code =
				"function foo takes integer bar returns integer\n" +
					"set bar=2\n" +
					"return 1\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}
	
	@Test
	public void setUndefinedVariable() {
		Compile compile = new Compile();
		String code =
				"function foo takes nothing returns nothing\n" +
					"set bar=2\n" +
				"endfunction";
		
		expectedEx.expect(UndefinedVariableException.class);
		expectedEx.expectMessage("2:4 Variable <bar> is not defined");
		
		compile.run(code);
	}

}
