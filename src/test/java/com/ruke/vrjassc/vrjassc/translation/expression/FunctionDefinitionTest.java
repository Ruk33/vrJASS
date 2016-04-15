package com.ruke.vrjassc.vrjassc.translation.expression;

import com.ruke.vrjassc.translator.expression.*;
import com.ruke.vrjassc.vrjassc.symbol.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionDefinitionTest {

	@Test
	public void test() {
		FunctionSymbol function = new FunctionSymbol("foo", null, null);
		FunctionDefinition translator = new FunctionDefinition(function);
		
		assertEquals(
			"function foo takes nothing returns nothing\n"
			+ "endfunction",
			translator.translate()
		);
		
		Type integer = new BuiltInTypeSymbol("integer", null, null);
		ClassSymbol bar = new ClassSymbol("bar", 1, null, null);
		Symbol baz = new LocalVariableSymbol("baz", null, null);
		
		baz.setType(integer);
		
		function.defineParam(bar);
		function.defineParam(baz);
		
		assertEquals(
			"function foo takes integer bar,integer baz returns nothing\n"
			+ "endfunction",
			translator.translate()
		);
		
		function.setType(integer);
		
		assertEquals(
			"function foo takes integer bar,integer baz returns integer\n"
			+ "endfunction",
			translator.translate()
		);
		
		function.setType(bar);
		
		assertEquals(
			"function foo takes integer bar,integer baz returns integer\n"
			+ "endfunction",
			translator.translate()
		);

		ExpressionList args = new ExpressionList();
		args.add(new RawExpression("1"));
		args.add(new RawExpression("2"));

		translator.add(new FunctionStatement(new FunctionExpression(function, false, args)));
		
		assertEquals(
			"function foo takes integer bar,integer baz returns integer\n"
				+ "call foo(1,2)\n"
			+ "endfunction",
			translator.translate()
		);
	}

}
