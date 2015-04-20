package test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import util.Compile;
import util.ProjectPath;

public class LibraryTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void correct() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"library foo\n"
				+ "function bar takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "private function nope takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "public function yep takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "endlibrary";
		
		String result =
				"function bar takes nothing returns nothing\n\n"
				+ "endfunction\n"
				+ "function foo__nope takes nothing returns nothing\n\n"
				+ "endfunction\n"
				+ "function foo_yep takes nothing returns nothing\n\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}

}
