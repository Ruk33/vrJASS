package com.ruke.vrjassc.vrjassc;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.AlreadyDefinedFunctionException;
import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.util.Compile;
import com.ruke.vrjassc.vrjassc.util.ProjectPath;

public class AlreadyDefinedFunctionTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();

		expectedEx.expect(AlreadyDefinedFunctionException.class);
		expectedEx.expectMessage("4:9 Function <add> already defined in 1:9");

		compile.runFromFile(testPath + "/AlreadyDefinedFunctionTest.txt");
	}

}
