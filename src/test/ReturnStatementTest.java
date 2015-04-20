package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Compile;

public class ReturnStatementTest {

	@Test
	public void test() {
		Compile compile = new Compile();
		String code =
				"function foo takes nothing returns integer\n" +
					"return 2\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}

}
