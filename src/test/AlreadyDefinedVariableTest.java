package test;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import util.Compile;
import util.ProjectPath;
import exception.AlreadyDefinedVariableException;
import exception.CompileException;

public class AlreadyDefinedVariableTest {
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		
		expectedEx.expect(AlreadyDefinedVariableException.class);
		expectedEx.expectMessage("1:49 Variable <a> already defined in 1:27");
		
		compile.runFromFile(testPath + "/AlreadyDefinedVariableTest.txt");
	}

}
