package test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.ElementNoAccessException;
import exception.IncorrectVariableTypeException;
import exception.InitializeArrayVariableException;
import exception.InvalidArrayVariableIndexException;
import exception.NoScopeVisibilityException;
import exception.VariableIsNotArrayException;
import util.Compile;

public class GlobalVariableTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void severalGlobalBlock() {
		Compile compile = new Compile();
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
	
	@Test
	public void privateOnNoScope() {
		Compile compile = new Compile();
		String code =
				"globals\n"
				+ "private integer foo\n"
				+ "endglobals";
		
		expectedEx.expect(NoScopeVisibilityException.class);
		expectedEx.expectMessage(
			"2:16 Element <foo> must be inside of an scope to declare visibility"
		);
		
		compile.run(code);
	}
	
	@Test
	public void publicOnNoScope() {
		Compile compile = new Compile();
		String code =
				"globals\n"
				+ "public integer foo\n"
				+ "endglobals";
		
		expectedEx.expect(NoScopeVisibilityException.class);
		expectedEx.expectMessage(
			"2:15 Element <foo> must be inside of an scope to declare visibility"
		);
		
		compile.run(code);
	}
	
	@Test
	public void publicAutoPrefixFromInsideOfScope() {
		Compile compile = new Compile();
		String code =
				"library foo\n"
				+ "globals\n"
				+ "public integer nope\n"
				+ "endglobals\n"
				+ "function bar takes nothing returns nothing\n"
				+ "set nope=2\n"
				+ "endfunction\n"
				+ "endlibrary";
		
		String result =
				"globals\n"
				+ "integer foo_nope\n"
				+ "endglobals\n"
				+ "function bar takes nothing returns nothing\n"
				+ "set foo_nope=2\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void privateAutoPrefixFromInsideOfScope() {
		Compile compile = new Compile();
		String code =
				"library foo\n"
				+ "globals\n"
				+ "private integer nope\n"
				+ "endglobals\n"
				+ "function bar takes nothing returns nothing\n"
				+ "set nope=2\n"
				+ "endfunction\n"
				+ "endlibrary";
		
		String result =
				"globals\n"
				+ "integer foo__nope\n"
				+ "endglobals\n"
				+ "function bar takes nothing returns nothing\n"
				+ "set foo__nope=2\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void privateUsingPrefix() {
		Compile compile = new Compile();
		String code =
				"library foo\n"
				+ "globals\n"
				+ "private integer nope=2\n"
				+ "endglobals\n"
				+ "endlibrary\n"
				
				+ "function bar takes nothing returns nothing\n"
				+ "local integer a=foo__nope\n"
				+ "endfunction";
		
		expectedEx.expect(ElementNoAccessException.class);
		expectedEx.expectMessage(
			"7:16 No access to element <foo__nope>"
		);
		
		compile.run(code);
	}

}
