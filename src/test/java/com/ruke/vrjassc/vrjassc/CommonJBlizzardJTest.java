package com.ruke.vrjassc.vrjassc;

import java.io.IOException;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.util.Compile;
import com.ruke.vrjassc.vrjassc.util.ProjectPath;

public class CommonJBlizzardJTest {

	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();

		compile.runFromFile(testPath + "/CommonJBlizzardJTest.txt");
	}

}
