package test;

import java.io.IOException;

import org.junit.Test;

import util.Compile;
import util.ProjectPath;
import exception.CompileException;

public class FunctionTakesNothingTest {

	
	@Test
	// dont know how to test this, but it shouldn't throw any syntax error
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		compile.runFromFile(testPath + "/FunctionTakesNothingTest.txt");
	}

}
