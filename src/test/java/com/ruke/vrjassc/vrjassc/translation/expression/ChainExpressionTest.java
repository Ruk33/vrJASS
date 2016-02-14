package com.ruke.vrjassc.vrjassc.translation.expression;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.translator.expression.ChainExpression;
import com.ruke.vrjassc.translator.expression.ExpressionList;
import com.ruke.vrjassc.translator.expression.FunctionExpression;
import com.ruke.vrjassc.translator.expression.VariableExpression;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.MethodSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.PropertySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.VrJassScope;

public class ChainExpressionTest {

	@Test
	public void property() {
		VrJassScope scope = new VrJassScope();
		ClassSymbol _class = new ClassSymbol("foo", 1, scope, null);
		PropertySymbol property = new PropertySymbol("baz", _class, null);
		MethodSymbol method = new MethodSymbol("bar", scope, null);
		LocalVariableSymbol _this = new LocalVariableSymbol("this", method, null);
		
		method.define(_this);
		_class.define(property);
		_class.define(method);
		scope.define(_class);
				
		ChainExpression translator = new ChainExpression();
		
		translator.append(new VariableExpression(_this, null), null);
		translator.append(new VariableExpression(property, null), null);
		
		assertEquals("LoadInteger(null,this,struct_foo_baz)", translator.translate());
		
		translator.setValue(new VariableExpression(new LocalVariableSymbol("bar", null, null), null));
		
		assertEquals("SaveInteger(null,this,struct_foo_baz,bar)", translator.translate());
		
		translator = new ChainExpression();
		property.setModifier(Modifier.STATIC, true);
		
		translator.append(new VariableExpression(_class, null), null);
		translator.append(new VariableExpression(property, null), null);
		
		assertEquals("struct_foo_baz", translator.translate());
		
		translator.setValue(new VariableExpression(new LocalVariableSymbol("bar", null, null), null));
		
		assertEquals("struct_foo_baz=bar", translator.translate());
	}
	
	@Test
	public void method() {
		VrJassScope scope = new VrJassScope();
		ClassSymbol foo = new ClassSymbol("foo", 1, scope, null);
		MethodSymbol bar = new MethodSymbol("bar", foo, null);
		Symbol _this = new LocalVariableSymbol("this", bar, null);
		
		_this.setType(foo);
		
		foo.define(bar);
		scope.define(foo);
		
		ChainExpression chainExpression = new ChainExpression();
		ExpressionList args = new ExpressionList();
		
		chainExpression.append(new VariableExpression(_this, null), null);
		chainExpression.append(new FunctionExpression(bar, false, args), null);
		
		args.add(new VariableExpression(new LocalVariableSymbol("baz", null, null), null));
		
		assertEquals("struct_foo_bar(this,baz)", chainExpression.translate());
		
		chainExpression = new ChainExpression();
		bar.setModifier(Modifier.STATIC, true);
		
		chainExpression.append(new VariableExpression(foo, null), null);
		chainExpression.append(new FunctionExpression(bar, false, args), null);
		
		assertEquals("struct_foo_bar(baz)", chainExpression.translate());
	}

}
