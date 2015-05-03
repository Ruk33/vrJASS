package com.ruke.vrjassc.vrjassc;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.exception.IncorrectReturnTypeFunctionException;
import com.ruke.vrjassc.vrjassc.exception.NoReturnFunctionException;
import com.ruke.vrjassc.vrjassc.util.Compile;
import com.ruke.vrjassc.vrjassc.util.ProjectPath;

public class FunctionReturnTypeTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void noReturn() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();

		expectedEx.expect(NoReturnFunctionException.class);
		expectedEx.expectMessage("1:0 Function <foo> must return <integer>");

		compile.runFromFile(testPath + "/NoReturnFunctionReturnTypeTest.txt");
	}

	@Test
	public void incorrectReturnType() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();

		expectedEx.expect(IncorrectReturnTypeFunctionException.class);
		expectedEx
				.expectMessage("3:4 Function <foo> must return <integer> but it is trying to return <string>");

		compile.runFromFile(testPath
				+ "/IncorrectReturnTypeFunctionReturnTypeTest.txt");
	}

}
