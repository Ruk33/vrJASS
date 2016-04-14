package com.ruke.vrjassc.vrjassc.translation.expression;

import com.ruke.vrjassc.translator.expression.BooleanExpression;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.VariableExpression;
import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BooleanExpressionTest {

	@Test
	public void integer() {
		Symbol s = new LocalVariableSymbol("foo", null, null);
		Type htype = new BuiltInTypeSymbol("integer", null, null);
		
		s.setType(htype);
		
		VariableExpression var = new VariableExpression(s, null);
		BooleanExpression bool = new BooleanExpression(var, null, null);
		
		assertEquals("foo!=0", bool.translate());
	}
	
	@Test
	public void string() {
		Symbol s = new LocalVariableSymbol("foo", null, null);
		Type htype = new BuiltInTypeSymbol("string", null, null);
		
		s.setType(htype);
		
		VariableExpression var = new VariableExpression(s, null);
		BooleanExpression bool = new BooleanExpression(var, null, null);
		
		assertEquals("StringLength(foo)!=0", bool.translate());
	}
	
	@Test
	public void handle() {
		Symbol s = new LocalVariableSymbol("foo", null, null);
		Type htype = new BuiltInTypeSymbol("unit", null, null);
		
		s.setType(htype);
		
		VariableExpression var = new VariableExpression(s, null);
		BooleanExpression bool = new BooleanExpression(var, null, null);
		
		assertEquals("foo!=null", bool.translate());
	}
	
	@Test
	public void test() {
		RawExpression five = new RawExpression("5");
		RawExpression four = new RawExpression("4");
		
		BooleanExpression translator;
		BooleanExpression.Operator operator;
		
		operator = BooleanExpression.Operator.EQUAL_EQUAL;
		translator = new BooleanExpression(five, operator, four);
		assertEquals("5==4", translator.translate());
		
		operator = BooleanExpression.Operator.NOT_EQUAL;
		translator = new BooleanExpression(five, operator, four);
		assertEquals("5!=4", translator.translate());
		
		operator = BooleanExpression.Operator.GREATER;
		translator = new BooleanExpression(five, operator, four);
		assertEquals("5>4", translator.translate());
		
		operator = BooleanExpression.Operator.GREATER_EQUAL;
		translator = new BooleanExpression(five, operator, four);
		assertEquals("5>=4", translator.translate());
		
		operator = BooleanExpression.Operator.LESS;
		translator = new BooleanExpression(five, operator, four);
		assertEquals("5<4", translator.translate());
		
		operator = BooleanExpression.Operator.LESS_EQUAL;
		translator = new BooleanExpression(five, operator, four);
		assertEquals("5<=4", translator.translate());
	}

}
