package com.ruke.vrjassc.vrjassc.translation.expression;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.translator.expression.ComparisonExpression;
import com.ruke.vrjassc.translator.expression.RawExpression;

public class ComparisonExpressionTest {

	@Test
	public void test() {
		RawExpression five = new RawExpression("5");
		RawExpression four = new RawExpression("4");
		
		ComparisonExpression translator;
		ComparisonExpression.Operator operator;
		
		operator = ComparisonExpression.Operator.EQUAL_EQUAL;
		translator = new ComparisonExpression(five, operator, four);
		assertEquals("5==4", translator.translate());
		
		operator = ComparisonExpression.Operator.NOT_EQUAL;
		translator = new ComparisonExpression(five, operator, four);
		assertEquals("5!=4", translator.translate());
		
		operator = ComparisonExpression.Operator.GREATER;
		translator = new ComparisonExpression(five, operator, four);
		assertEquals("5>4", translator.translate());
		
		operator = ComparisonExpression.Operator.GREATER_EQUAL;
		translator = new ComparisonExpression(five, operator, four);
		assertEquals("5>=4", translator.translate());
		
		operator = ComparisonExpression.Operator.LESS;
		translator = new ComparisonExpression(five, operator, four);
		assertEquals("5<4", translator.translate());
		
		operator = ComparisonExpression.Operator.LESS_EQUAL;
		translator = new ComparisonExpression(five, operator, four);
		assertEquals("5<=4", translator.translate());
	}

}
