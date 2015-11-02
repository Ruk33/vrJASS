package com.ruke.vrjassc.vrjassc.translation.expression;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.translator.expression.Expression;
import com.ruke.vrjassc.translator.expression.FunctionDefinition;
import com.ruke.vrjassc.translator.expression.FunctionStatement;
import com.ruke.vrjassc.translator.expression.IfStatement;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.StatementBody;
import com.ruke.vrjassc.translator.expression.VariableStatement;
import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class IfStatementTest {

	@Test
	public void test() {
		StatementBody func = new FunctionDefinition(new FunctionSymbol("foo", null, null));
		
		Expression cond = new RawExpression("true");
		IfStatement ifstat = new IfStatement(cond);
		
		assertEquals("if true then\nendif", ifstat.translate());
		
		Symbol baz = new LocalVariableSymbol("baz", null, null);
		baz.setType(new BuiltInTypeSymbol("integer", null, null));
		
		ifstat.add(new FunctionStatement(new RawExpression("foo()")));
		ifstat.add(new VariableStatement(baz, new RawExpression("bar")));
		
		func.add(ifstat);
		
		assertEquals(
			"function foo takes nothing returns nothing\n"
				+ "local integer baz\n"
				+ "if true then\n"
					+ "call foo()\n"
					+ "set baz=bar\n"
				+ "endif\n"
			+ "endfunction",
			func.translate()
		);
	}

}
