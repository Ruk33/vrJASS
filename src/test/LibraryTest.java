package test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import util.Compile;

public class LibraryTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void correct() {
		Compile compile = new Compile();
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
	
	@Test
	public void noCollisionPublic() {
		Compile compile = new Compile();
		String code =
				"library foo\n"
				+ "public function yep takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "endlibrary\n"
				+ "library bar\n"
				+ "public function yep takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "endlibrary";
		
		String result =
				"function foo_yep takes nothing returns nothing\n\n"
				+ "endfunction\n"
				+ "function bar_yep takes nothing returns nothing\n\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void noCollisionPrivate() {
		Compile compile = new Compile();
		String code =
				"library foo\n"
				+ "private function yep takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "endlibrary\n"
				+ "library bar\n"
				+ "private function yep takes nothing returns nothing\n"
				+ "endfunction\n"
				+ "endlibrary";
		
		String result =
				"function foo__yep takes nothing returns nothing\n\n"
				+ "endfunction\n"
				+ "function bar__yep takes nothing returns nothing\n\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void requires() {
		Compile compile = new Compile();
		String code =
				"library foo requires bar, other\n"
				+ "endlibrary\n"
				+ "library bar\n"
				+ "endlibrary\n"
				+ "library other\n"
				+ "endlibrary";
				
		assertEquals("\n\n", compile.run(code));
	}

}
