package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Compile;
import util.ProjectPath;

public class MathExpressionTest {

	@Test
	public void test() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes integer a returns nothing\n" +
					"set a=a*a*4\n" +
					"set a=a/a/4\n" +
					"set a=a-a-4\n" +
					"set a=a+a+4\n" +
					"set a=a*5/4-2+4\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}

}
