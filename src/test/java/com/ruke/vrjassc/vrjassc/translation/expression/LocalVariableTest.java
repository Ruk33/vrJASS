package com.ruke.vrjassc.vrjassc.translation.expression;

import com.ruke.vrjassc.translator.expression.Statement;
import com.ruke.vrjassc.translator.expression.VariableExpression;
import com.ruke.vrjassc.translator.expression.VariableStatement;
import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocalVariableTest {

	@Test
	public void test() {
		Symbol variable = new LocalVariableSymbol("i", null, null);
		variable.setType(new BuiltInTypeSymbol("integer", null, null));
		
		Statement translator = new VariableStatement(variable, null);
		
		assertEquals("local integer i=0", translator.translate());
		
		translator = new VariableStatement(variable, new VariableExpression(variable, null));
		
		assertEquals("local integer i=i", translator.translate());
	}

}
