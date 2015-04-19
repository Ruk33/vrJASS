package test;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import util.Compile;
import util.ProjectPath;
import exception.CompileException;
import exception.IncorrectArgumentTypeFunctionCallException;

public class FunctionIncorrectArgumentTypeTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		
		expectedEx.expect(IncorrectArgumentTypeFunctionCallException.class);
		expectedEx.expectMessage(
			"6:13 Incorrect argument type, given <string> but should be <integer>"
		);
		
		compile.runFromFile(testPath + "/FunctionIncorrectArgumentTypeTest.txt");
	}

}
