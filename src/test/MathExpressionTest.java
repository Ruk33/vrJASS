package test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.MathematicalExpressionException;
import util.Compile;
import util.ProjectPath;

public class MathExpressionTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void correct() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes integer a returns nothing\n" +
					"set a=a*a*4\n" +
					"set a=a/a/4\n" +
					"set a=a-a-4\n" +
					"set a=a+a+4\n" +
					"set a=a*5/4-2+4\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}
	
	@Test
	public void wrongDiv() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes integer a returns nothing\n" +
					"set a=a/\"nope\"\n" +
				"endfunction";
		
		expectedEx.expect(MathematicalExpressionException.class);
		expectedEx.expectMessage(
			"2:8 Incorrect mathematical expression (only integers and reals)"
		);
		
		compile.run(code);
	}
	
	@Test
	public void wrongMult() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes integer a returns nothing\n" +
					"set a=a*\"nope\"\n" +
				"endfunction";
		
		expectedEx.expect(MathematicalExpressionException.class);
		expectedEx.expectMessage(
			"2:8 Incorrect mathematical expression (only integers and reals)"
		);
		
		compile.run(code);
	}
	
	@Test
	public void wrongMinus() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes integer a returns nothing\n" +
					"set a=a-\"nope\"\n" +
				"endfunction";
		
		expectedEx.expect(MathematicalExpressionException.class);
		expectedEx.expectMessage(
			"2:8 Incorrect mathematical expression (only integers and reals)"
		);
		
		compile.run(code);
	}
	
	@Test
	public void wrongPlus() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes integer a returns nothing\n" +
					"set a=a+\"nope\"\n" +
				"endfunction";
		
		expectedEx.expect(MathematicalExpressionException.class);
		expectedEx.expectMessage(
			"2:8 Incorrect mathematical expression (only integers and reals)"
		);
		
		compile.run(code);
	}

}
