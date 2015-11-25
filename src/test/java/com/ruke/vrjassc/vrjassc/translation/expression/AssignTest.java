package com.ruke.vrjassc.vrjassc.translation.expression;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.translator.expression.AssignmentStatement;
import com.ruke.vrjassc.translator.expression.ChainExpression;
import com.ruke.vrjassc.translator.expression.Expression;
import com.ruke.vrjassc.translator.expression.VariableExpression;
import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class AssignTest {

	@Test
	public void property() {
		ChainExpression chain = new ChainExpression();
		
		Expression foo = new VariableExpression(new Symbol("foo", null, null), null);
		Expression bar = new VariableExpression(new Symbol("bar", null, null), null);
		
		Expression value = new VariableExpression(new Symbol("baz", null, null), null);
		
		// foo would be the instance
		// set foo.bar = value
		chain.append(foo, null);
		chain.append(bar, null);
		chain.setValue(value);
		
		AssignmentStatement translator = new AssignmentStatement(chain, value);
		
		assertEquals("call SaveInteger(null,foo,bar,baz)", translator.translate());
	}
	
	@Test
	public void simple() {
		Expression foo = new VariableExpression(new Symbol("foo", null, null), null);
		Expression bar = new VariableExpression(new Symbol("bar", null, null), null);
		
		AssignmentStatement translator = new AssignmentStatement(foo, bar);
		
		assertEquals("set foo=bar", translator.translate());
		
		foo = new VariableExpression(new Symbol("foo", null, null), bar);
		translator = new AssignmentStatement(foo, bar);
		
		assertEquals("set foo[bar]=bar", translator.translate());
	}

}
