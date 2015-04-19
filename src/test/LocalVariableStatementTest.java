package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Compile;
import util.ProjectPath;

public class LocalVariableStatementTest {

	@Test
	public void test() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes integer bar returns integer\n" +
					"local integer bar=2\n" +
					"local integer nop\n" +
					"return 1\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}

}
