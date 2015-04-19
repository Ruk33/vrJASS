package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Compile;
import util.ProjectPath;

public class LocalArrayVariableStatementTest {

	@Test
	public void defineArray() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes nothing returns nothing\n" +
					"local integer array bar\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}
	
	@Test
	public void setArray() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes nothing returns nothing\n" +
					"local integer array bar\n" +
					"set bar[1+1]=2\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}

}
