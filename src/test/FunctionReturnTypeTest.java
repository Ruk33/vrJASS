package test;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import util.Compile;
import util.ProjectPath;
import exception.CompileException;
import exception.IncorrectReturnTypeFunctionException;
import exception.NoReturnFunctionException;

public class FunctionReturnTypeTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void noReturn() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		
		expectedEx.expect(NoReturnFunctionException.class);
		expectedEx.expectMessage(
			"1:0 Function <foo> must return <integer>"
		);
		
		compile.runFromFile(testPath + "/NoReturnFunctionReturnTypeTest.txt");
	}
	
	@Test
	public void incorrectReturnType() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		
		expectedEx.expect(IncorrectReturnTypeFunctionException.class);
		expectedEx.expectMessage(
			"3:4 Function <foo> must return <integer> but it is trying to return <string>"
		);
		
		compile.runFromFile(testPath + "/IncorrectReturnTypeFunctionReturnTypeTest.txt");
	}

}
