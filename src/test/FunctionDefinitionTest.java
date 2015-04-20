package test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.NoScopeVisibilityException;
import util.Compile;
import util.ProjectPath;

public class FunctionDefinitionTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void correct() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes nothing returns nothing\n\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}
	
	@Test
	public void privateOnNoScope() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"private function foo takes nothing returns nothing\n\n" +
				"endfunction";
		
		expectedEx.expect(NoScopeVisibilityException.class);
		expectedEx.expectMessage(
			"1:17 Function <foo> must be inside of an scope to declare visibility"
		);
		
		compile.run(code);
	}
	
	@Test
	public void publicOnNoScope() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"public function foo takes nothing returns nothing\n\n" +
				"endfunction";
		
		expectedEx.expect(NoScopeVisibilityException.class);
		expectedEx.expectMessage(
			"1:16 Function <foo> must be inside of an scope to declare visibility"
		);
		
		compile.run(code);
	}

}
