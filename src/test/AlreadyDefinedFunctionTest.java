package test;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import util.Compile;
import util.ProjectPath;
import exception.AlreadyDefinedFunctionException;
import exception.CompileException;

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
