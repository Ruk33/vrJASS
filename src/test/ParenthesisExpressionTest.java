package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Compile;
import util.ProjectPath;

public class ParenthesisExpressionTest {

	@Test
	public void test() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes integer i returns integer\n" +
					"return foo((4+i)+2)\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}

}
