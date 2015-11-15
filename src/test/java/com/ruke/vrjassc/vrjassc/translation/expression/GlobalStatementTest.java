package com.ruke.vrjassc.vrjassc.translation.expression;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.translator.expression.GlobalStatement;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.StatementList;
import com.ruke.vrjassc.translator.expression.VariableStatement;
import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;

public class GlobalStatementTest {

	@Test
	public void empty() {
		StatementList globals = new GlobalStatement();
		assertEquals("globals\nendglobals", globals.translate());
	}
	
	@Test
	public void test() {
		StatementList globals = new GlobalStatement();
		
		Symbol foo = new Symbol("foo", null, null);
		Symbol bar = new Symbol("bar", null, null);
		
		Type integer = new BuiltInTypeSymbol("integer", null, null);
		
		foo.setType(integer);
		bar.setType(integer);
		
		globals.add(new VariableStatement(foo, null));
		globals.add(new VariableStatement(bar, new RawExpression("baz")));
		
		assertEquals(
			"globals\n"
			+ "integer foo=0\n"
			+ "integer bar=baz\n"
			+ "endglobals",
			globals.translate()
		);
	}

}
