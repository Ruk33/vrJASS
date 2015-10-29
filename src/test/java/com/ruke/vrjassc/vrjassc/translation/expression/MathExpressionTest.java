package com.ruke.vrjassc.vrjassc.translation.expression;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.translator.expression.MathExpression;
import com.ruke.vrjassc.translator.expression.RawExpression;

public class MathExpressionTest {

	@Test
	public void test() {
		RawExpression five = new RawExpression("5");
		RawExpression four = new RawExpression("4");
		
		MathExpression translator;
		MathExpression.Operator operator;
		
		operator = MathExpression.Operator.PLUS;
		translator = new MathExpression(five, operator, four);
		assertEquals("5+4", translator.translate());
		
		operator = MathExpression.Operator.MINUS;
		translator = new MathExpression(five, operator, four);
		assertEquals("5-4", translator.translate());
		
		operator = MathExpression.Operator.MULT;
		translator = new MathExpression(five, operator, four);
		assertEquals("5*4", translator.translate());
		
		operator = MathExpression.Operator.DIV;
		translator = new MathExpression(five, operator, four);
		assertEquals("5/4", translator.translate());
	}

}
