package com.ruke.vrjassc.vrjassc;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.exception.TooManyArgumentsFunctionCallException;
import com.ruke.vrjassc.vrjassc.util.Compile;
import com.ruke.vrjassc.vrjassc.util.ProjectPath;

public class FunctionTooManyArgumentsTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();

		expectedEx.expect(TooManyArgumentsFunctionCallException.class);
		expectedEx
				.expectMessage("7:9 Too many arguments passed to function <add>");

		compile.runFromFile(testPath + "/FunctionTooManyArgumentsTest.txt");
	}

}
