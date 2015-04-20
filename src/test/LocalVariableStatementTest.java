package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Compile;

public class LocalVariableStatementTest {

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code =
				"function foo takes integer i returns integer\n" +
					"local integer bar=2\n" +
					"local integer nop\n" +
					"return 1\n" +
				"endfunction";
		
		assertEquals(code, compile.run(code));
	}
	
	@Test
	public void freeDeclaration() {
		Compile compile = new Compile();
		String code =
				"function foo takes nothing returns nothing\n" +
					"call foo()\n" +
					"call foo()\n" +
					"local integer a=5\n" +
					"local integer b=6\n" +
				"endfunction";
		
		String result =
				"function foo takes nothing returns nothing\n" +
					"local integer a=5\n" +
					"local integer b=6\n" +
					"call foo()\n" +
					"call foo()\n" +
				"endfunction";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void compoundOperator() {
		Compile compile = new Compile();
		String code =
				"function foo takes nothing returns nothing\n" +
					"local integer i\n" +
					"set i/=5\n" +
					"set i*=6\n" +
					"set i-=6\n" +
					"set i+=6\n" +
					"local integer array bar\n" +
					"set bar[1+3]+=4\n" +
				"endfunction";
		
		String result =
				"function foo takes nothing returns nothing\n" +
					"local integer i\n" +
					"local integer array bar\n" +
					"set i=i/5\n" +
					"set i=i*6\n" +
					"set i=i-6\n" +
					"set i=i+6\n" +
					"set bar[1+3]=bar[1+3]+4\n" +
				"endfunction";
		
		assertEquals(result, compile.run(code));
	}

}
