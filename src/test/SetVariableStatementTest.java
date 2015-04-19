package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Compile;
import util.ProjectPath;

public class SetVariableStatementTest {

	@Test
	public void test() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes integer bar returns integer\n" +
					"set bar=2\n" +
					"return 1\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}

}
