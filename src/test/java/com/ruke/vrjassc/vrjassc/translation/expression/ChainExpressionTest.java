package com.ruke.vrjassc.vrjassc.translation.expression;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.translator.expression.ChainExpression;
import com.ruke.vrjassc.translator.expression.Expression;
import com.ruke.vrjassc.translator.expression.ExpressionList;
import com.ruke.vrjassc.translator.expression.FunctionExpression;
import com.ruke.vrjassc.translator.expression.VariableExpression;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.MethodSymbol;
import com.ruke.vrjassc.vrjassc.symbol.PropertySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class ChainExpressionTest {

	@Test
	public void property() {
		Expression _this = new VariableExpression(new LocalVariableSymbol("this", null, null), null);
		Expression foo = new VariableExpression(new PropertySymbol("foo", null, null), null);
		
		ChainExpression translator = new ChainExpression();
		
		translator.append(_this, null);
		translator.append(foo, null);
		
		assertEquals("LoadInteger(null,this,foo)", translator.translate());
		
		translator.setValue(new VariableExpression(new LocalVariableSymbol("bar", null, null), null));
		
		assertEquals("SaveInteger(null,this,foo,bar)", translator.translate());
	}
	
	@Test
	public void method() {
		ClassSymbol foo = new ClassSymbol("foo", null, null);
		MethodSymbol bar = new MethodSymbol("bar", foo, null);
		Symbol _this = new LocalVariableSymbol("this", null, null);
		
		_this.setType(foo);
		
		ChainExpression chainExpression = new ChainExpression();
		ExpressionList args = new ExpressionList();
		
		chainExpression.append(new VariableExpression(_this, null), null);
		chainExpression.append(new FunctionExpression(bar, false, args), null);
		
		args.add(new VariableExpression(new LocalVariableSymbol("baz", null, null), null));
		
		assertEquals("struct_foo_bar(this,baz)", chainExpression.translate());
	}

}
