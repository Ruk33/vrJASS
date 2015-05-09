package com.ruke.vrjassc.vrjassc;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.exception.UndefinedVariableException;
import com.ruke.vrjassc.vrjassc.util.Compile;
import com.ruke.vrjassc.vrjassc.util.ProjectPath;

public class UndefinedVariableTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void test() throws CompileException, IOException {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();

		expectedEx.expect(UndefinedVariableException.class);
		expectedEx.expectMessage("2:11 Variable <a> is not defined");

		compile.runFromFile(testPath + "/UndefinedVariableTest.txt");
	}

	@Test
	public void notNoAccessToElement() throws CompileException, IOException {
		Compile compile = new Compile();
		String code = "library foo\n"
			+ "function lorem takes string str returns nothing\n"
			+ "endfunction\n"
		    + "public function bar takes nothing returns nothing\n"
		    + "call lorem(\"damn\" + str)\n"
		    + "endfunction\n"
		    + "endlibrary";

		expectedEx.expect(UndefinedVariableException.class);
		expectedEx.expectMessage("5:20 Variable <str> is not defined");

		compile.run(code);
	}

}
