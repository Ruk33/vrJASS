package com.ruke.vrjassc.vrjassc.translation.expression;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.translator.expression.GlobalStatement;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.StatementBody;
import com.ruke.vrjassc.translator.expression.VariableStatement;
import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;

public class GlobalStatementTest {

	@Test
	public void empty() {
		StatementBody globals = new GlobalStatement();
		assertEquals("", globals.translate());
	}
	
	@Test
	public void test() {
		StatementBody globals = new GlobalStatement();
		
		Symbol foo = new Symbol("foo", null, null);
		Symbol bar = new Symbol("bar", null, null);
		
		Type integer = new BuiltInTypeSymbol("integer", null, null);
		
		foo.setType(integer);
		bar.setType(integer);
		
		globals.append(new VariableStatement(foo, null));
		globals.append(new VariableStatement(bar, new RawExpression("baz")));
		
		assertEquals(
			"globals\n"
			+ "integer foo\n"
			+ "integer bar=baz\n"
			+ "endglobals",
			globals.translate()
		);
	}

}
