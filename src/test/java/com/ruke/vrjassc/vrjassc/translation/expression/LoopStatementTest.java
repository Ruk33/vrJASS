package com.ruke.vrjassc.vrjassc.translation.expression;

import com.ruke.vrjassc.translator.expression.ExitWhenStatement;
import com.ruke.vrjassc.translator.expression.FunctionStatement;
import com.ruke.vrjassc.translator.expression.LoopStatement;
import com.ruke.vrjassc.translator.expression.RawExpression;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoopStatementTest {

	@Test
	public void test() {
		LoopStatement loop = new LoopStatement();
		
		loop.add(new FunctionStatement(new RawExpression("foo()")));
		loop.add(new ExitWhenStatement(new RawExpression("false")));
		
		assertEquals(
			"loop\n"
				+ "call foo()\n"
				+ "exitwhen false\n"
			+ "endloop",
			loop.translate()
		);
	}

}
