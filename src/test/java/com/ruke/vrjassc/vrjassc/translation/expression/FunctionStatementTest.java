package com.ruke.vrjassc.vrjassc.translation.expression;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.translator.expression.ChainExpression;
import com.ruke.vrjassc.translator.expression.Expression;
import com.ruke.vrjassc.translator.expression.ExpressionList;
import com.ruke.vrjassc.translator.expression.FunctionExpression;
import com.ruke.vrjassc.translator.expression.FunctionStatement;
import com.ruke.vrjassc.translator.expression.Statement;
import com.ruke.vrjassc.translator.expression.VariableExpression;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.MethodSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Scope;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class FunctionStatementTest {

	@Test
	public void function() {
		FunctionSymbol foo = new FunctionSymbol("foo", null, null);
		Symbol bar = new LocalVariableSymbol("bar", foo, null);
		Symbol baz = new LocalVariableSymbol("baz", foo, null);
		
		ExpressionList args = new ExpressionList();
		FunctionExpression function = new FunctionExpression(foo, false, args);
		FunctionStatement translator = new FunctionStatement(function);
		
		assertEquals("call foo()", translator.translate());
		
		args.add(new VariableExpression(bar, null));
		args.add(new VariableExpression(baz, null));
		
		assertEquals("call foo(bar,baz)", translator.translate());
	}
	
	@Test
	public void method() {
		Scope foo = new ClassSymbol("foo", null, null);
		FunctionSymbol bar = new MethodSymbol("bar", foo, null);
		
		ChainExpression chain = new ChainExpression();
		
		Expression function = new FunctionExpression(bar, false, new ExpressionList());
		Expression _this = new VariableExpression(new LocalVariableSymbol("this", bar, null), null);
		
		chain.append(_this, null);
		chain.append(function, null);
		
		Statement translator = new FunctionStatement(chain);
		
		assertEquals("call struct_foo_bar(this)", translator.translate());
	}

}
