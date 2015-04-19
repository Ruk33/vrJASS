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
	public void parameters() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		
		expectedEx.expect(AlreadyDefinedVariableException.class);
		expectedEx.expectMessage("1:49 Variable <a> already defined in 1:27");
		
		compile.runFromFile(testPath + "/AlreadyDefinedVariableTest.txt");
	}
	
	@Test
	public void locals() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes nothing returns nothing\n" +
					"local integer bar\n" +
					"local integer bar\n" +
				"endfunction";
		
		expectedEx.expect(AlreadyDefinedVariableException.class);
		expectedEx.expectMessage("3:14 Variable <bar> already defined in 2:14");
		
		compile.run(code);
	}

}
