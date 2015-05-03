package com.ruke.vrjassc.vrjassc;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.exception.IncorrectArgumentTypeFunctionCallException;
import com.ruke.vrjassc.vrjassc.util.Compile;
import com.ruke.vrjassc.vrjassc.util.ProjectPath;

public class FunctionIncorrectArgumentTypeTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();

		expectedEx.expect(IncorrectArgumentTypeFunctionCallException.class);
		expectedEx
				.expectMessage("6:13 Incorrect argument type, given <string> but should be <integer>");

		compile.runFromFile(testPath + "/FunctionIncorrectArgumentTypeTest.txt");
	}

}
