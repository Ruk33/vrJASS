package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import util.Compile;
import util.ProjectPath;
import exception.CompileException;

public class SeveralEmptyLinesTest {

	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String result = compile.runFromFile(testPath + "/SeveralEmptyLinesTest.txt");
		
		assertEquals(
			"function x takes integer i returns integer" + "\n"
				+ "return 1" + "\n" +
			"endfunction", 
			result
		);
	}

}
