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
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.VrJassScope;

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
		VrJassScope scope = new VrJassScope();
		ClassSymbol foo = new ClassSymbol("foo", 1, scope, null);
		MethodSymbol bar = new MethodSymbol("bar", foo, null);
		LocalVariableSymbol _this = new LocalVariableSymbol("this", bar, null);
		
		_this.setType(foo);
		foo.define(bar);
		scope.define(foo);
		
		ChainExpression chain = new ChainExpression();
		
		Expression function = new FunctionExpression(bar, false, new ExpressionList());
		
		chain.append(new VariableExpression(_this, null), null);
		chain.append(function, null);
		
		Statement translator = new FunctionStatement(chain);
		
		assertEquals("call struct_foo_bar(this)", translator.translate());
	}

}
