package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Compile;
import util.ProjectPath;

public class FunctionCallTest {

	@Test
	public void test() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes nothing returns nothing\n" +
					"call foo()\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}

}
