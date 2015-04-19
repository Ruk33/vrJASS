package test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.InvalidArrayVariableIndexException;
import exception.VariableIsNotArrayException;
import util.Compile;
import util.ProjectPath;

public class LocalArrayVariableStatementTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
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
	
	@Test
	public void setNonArrayVariable() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes nothing returns nothing\n" +
					"local integer bar\n" +
					"set bar[1+1]=2\n" +
				"endfunction";
		
		expectedEx.expect(VariableIsNotArrayException.class);
		expectedEx.expectMessage("3:4 Variable <bar> is not an array");
		
		compile.run(code);
	}
	
	@Test
	public void setInvalidIndex() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes nothing returns nothing\n" +
					"local integer array bar\n" +
					"set bar[\"nope\"]=2\n" +
				"endfunction";
		
		expectedEx.expect(InvalidArrayVariableIndexException.class);
		expectedEx.expectMessage("3:8 Invalid index (only integer type)");
		
		compile.run(code);
	}

}
