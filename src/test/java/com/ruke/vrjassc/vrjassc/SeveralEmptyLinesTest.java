package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.util.Compile;
import com.ruke.vrjassc.vrjassc.util.ProjectPath;

public class SeveralEmptyLinesTest {

	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String result = compile.runFromFile(testPath
				+ "/SeveralEmptyLinesTest.txt");

		assertEquals(
				"function x takes integer i returns integer" + System.lineSeparator()
				+ "return 1" + System.lineSeparator()
				+ "endfunction",
				result
		);
	}

}
