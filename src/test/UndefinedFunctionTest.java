package test;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import util.Compile;
import util.ProjectPath;
import exception.CompileException;
import exception.UndefinedFunctionException;

public class UndefinedFunctionTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		
		expectedEx.expect(UndefinedFunctionException.class);
		expectedEx.expectMessage("2:9 Function <z> is not defined");
		
		compile.runFromFile(testPath + "/UndefinedFunctionTest.txt");
	}
	
}
