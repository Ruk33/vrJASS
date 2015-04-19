package test;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import util.Compile;
import util.ProjectPath;
import exception.CompileException;
import exception.TooFewArgumentsFunctionCallException;

public class FunctionTooFewArgumentsTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		
		expectedEx.expect(TooFewArgumentsFunctionCallException.class);
		expectedEx.expectMessage("6:9 Too few arguments passed to function <add>");
		
		compile.runFromFile(testPath + "/FunctionTooFewArgumentsTest.txt");
	}

}
