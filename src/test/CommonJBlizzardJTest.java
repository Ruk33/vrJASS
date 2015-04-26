package test;

import java.io.IOException;

import org.junit.Test;

import util.Compile;
import util.ProjectPath;
import exception.CompileException;

public class CommonJBlizzardJTest {

	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		
		compile.runFromFile(testPath + "/CommonJBlizzardJTest.txt");
	}

}
