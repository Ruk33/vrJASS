package test;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import util.Compile;
import util.ProjectPath;
import exception.CompileException;
import exception.NoReturnFunctionException;

public class FunctionReturnTypeTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		
		expectedEx.expect(NoReturnFunctionException.class);
		expectedEx.expectMessage(
			"1:0 Function <foo> must return <integer>"
		);
		
		compile.runFromFile(testPath + "/NoReturnFunctionReturnTypeTest.txt");
	}

}
