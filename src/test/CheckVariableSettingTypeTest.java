package test;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import util.Compile;
import util.ProjectPath;
import exception.CompileException;
import exception.IncorrectVariableTypeException;

public class CheckVariableSettingTypeTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		
		expectedEx.expect(IncorrectVariableTypeException.class);
		expectedEx.expectMessage(
			"2:8 Variable <bar> must have a value of type <integer>, but <string> type given"
		);
		
		compile.runFromFile(testPath + "/CheckVariableSettingTypeTest.txt");
	}

}
