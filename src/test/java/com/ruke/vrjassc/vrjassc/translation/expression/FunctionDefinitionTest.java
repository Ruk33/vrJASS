package com.ruke.vrjassc.vrjassc.translation.expression;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.translator.expression.ExpressionList;
import com.ruke.vrjassc.translator.expression.FunctionDefinition;
import com.ruke.vrjassc.translator.expression.FunctionExpression;
import com.ruke.vrjassc.translator.expression.FunctionStatement;
import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;

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
		ClassSymbol bar = new ClassSymbol("bar", null, null);
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
		
		translator.add(new FunctionStatement(new FunctionExpression(function, false, new ExpressionList())));
		
		assertEquals(
			"function foo takes integer bar,integer baz returns integer\n"
				+ "call foo()\n"
			+ "endfunction",
			translator.translate()
		);
	}

}
