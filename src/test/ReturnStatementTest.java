package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Compile;
import util.ProjectPath;

public class ReturnStatementTest {

	@Test
	public void test() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes nothing returns integer\n" +
					"return 2\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}

}
