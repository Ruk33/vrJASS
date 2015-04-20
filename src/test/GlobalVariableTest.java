package test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.IncorrectVariableTypeException;
import exception.InitializeArrayVariableException;
import exception.InvalidArrayVariableIndexException;
import exception.VariableIsNotArrayException;
import util.Compile;
import util.ProjectPath;

public class GlobalVariableTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void severalGlobalBlock() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"globals\n"
				+ "integer foo=3\n"
				+ "endglobals\n"
				+ "function bar takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "globals\n"
				+ "integer e\n"
				+ "endglobals\n"
				+ "globals\n"
				+ "integer a=9\n"
				+ "endglobals";
		
		String result =
				"globals\n"
				+ "integer foo=3\n"
				+ "integer e\n"
				+ "integer a=9\n"
				+ "endglobals\n"
				+ "function bar takes nothing returns nothing\n\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void declaration() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"globals\n"
				+ "\n\n"
				+ "integer foo=2\n"
				+ "endglobals";
		
		String result =
				"globals\n"
				+ "integer foo=2\n"
				+ "endglobals";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void setArray() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"globals\n"
				+ "integer array bar\n"
				+ "endglobals\n"
				+ "function foo takes nothing returns nothing\n"
				+ "set bar[1]=2\n"
				+ "endfunction";
		
		assertEquals(code, compile.run(code));
	}
	
	@Test
	public void setting() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"globals\n"
				+ "\n\n"
				+ "integer foo=2\n"
				+ "endglobals\n\n\n"
				+ "function bar takes nothing returns nothing\n"
				+ "set foo=3\n"
				+ "endfunction";
		
		String result =
				"globals\n"
				+ "integer foo=2\n"
				+ "endglobals\n"
				+ "function bar takes nothing returns nothing\n"
				+ "set foo=3\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void setNonArray() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes nothing returns nothing\n"
				+ "set bar[1]=2\n"
				+ "endfunction\n"
				+ "globals\n"
				+ "integer bar\n"
				+ "endglobals";
		
		expectedEx.expect(VariableIsNotArrayException.class);
		expectedEx.expectMessage("2:4 Variable <bar> is not an array");
		
		compile.run(code);
	}
	
	@Test
	public void setInvalidIndex() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"globals\n"
				+ "integer array bar\n"
				+ "endglobals\n"
				+ "function foo takes nothing returns nothing\n"
				+ "set bar[\"nope\"]=2\n"
				+ "endfunction";
		
		expectedEx.expect(InvalidArrayVariableIndexException.class);
		expectedEx.expectMessage("5:8 Invalid index (only integer type)");
		
		compile.run(code);
	}
	
	@Test
	public void useInvalidIndex() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"globals\n"
				+ "integer array bar\n"
				+ "endglobals\n"
				+ "function foo takes nothing returns nothing\n"
				+ "set bar[1]=bar[\"nope\"]\n"
				+ "endfunction";
		
		expectedEx.expect(InvalidArrayVariableIndexException.class);
		expectedEx.expectMessage("5:15 Invalid index (only integer type)");
		
		compile.run(code);
	}
	
	@Test
	public void initializeAtDeclaration() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"globals\n"
				+ "integer array bar=1\n"
				+ "endglobals\n";
		
		expectedEx.expect(InitializeArrayVariableException.class);
		expectedEx.expectMessage("2:14 Arrays can not be initialized on declaration");
		
		compile.run(code);
	}
	
	@Test
	public void type() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"globals\n"
				+ "integer bar=\"nope\"\n"
				+ "endglobals\n";
		
		expectedEx.expect(IncorrectVariableTypeException.class);
		expectedEx.expectMessage(
			"2:8 Variable <bar> must have a value of type <integer>, but <string> type given"
		);
		
		compile.run(code);
	}
	
	@Test
	public void inScope() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"library foo\n"
				+ "globals\n"
				+ "integer bar\n"
				+ "private integer nope\n"
				+ "public integer yep\n"
				+ "endglobals\n"
				+ "endlibrary";
		
		String result =
				"globals\n"
				+ "integer bar\n"
				+ "integer foo__nope\n"
				+ "integer foo_yep\n"
				+ "endglobals";
		
		assertEquals(result, compile.run(code));
	}

}
