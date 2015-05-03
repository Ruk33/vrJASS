package com.ruke.vrjassc.vrjassc;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.exception.IncorrectVariableTypeException;
import com.ruke.vrjassc.vrjassc.util.Compile;
import com.ruke.vrjassc.vrjassc.util.ProjectPath;

public class CheckLocalVariableSettingTypeTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();

		expectedEx.expect(IncorrectVariableTypeException.class);
		expectedEx
				.expectMessage("2:18 Variable <bar> must have a value of type <integer>, but <string> type given");

		compile.runFromFile(testPath + "/CheckLocalVariableSettingTypeTest.txt");
	}

}
