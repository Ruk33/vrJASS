package com.ruke.vrjassc.vrjassc.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.translator.expression.AssignmentStatement;
import com.ruke.vrjassc.translator.expression.Expression;
import com.ruke.vrjassc.translator.expression.ExpressionList;
import com.ruke.vrjassc.translator.expression.FunctionDefinition;
import com.ruke.vrjassc.translator.expression.FunctionExpression;
import com.ruke.vrjassc.translator.expression.FunctionStatement;
import com.ruke.vrjassc.translator.expression.JassContainer;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.ReturnStatement;
import com.ruke.vrjassc.translator.expression.Statement;
import com.ruke.vrjassc.translator.expression.StatementBody;
import com.ruke.vrjassc.translator.expression.VariableExpression;
import com.ruke.vrjassc.translator.expression.VariableStatement;
import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;

public class StatementOrderTest {

	@Test
	public void localVariable() {
		JassContainer container = new JassContainer();
		
		FunctionSymbol fooSymbol = new FunctionSymbol("foo", null, null);
		StatementBody fooDef = new FunctionDefinition(fooSymbol);
		
		Type integer = new BuiltInTypeSymbol("integer", null, null);
		Symbol barSymbol = new LocalVariableSymbol("bar", null, null);
		
		barSymbol.setType(integer);
		
		Expression barExpression = new VariableExpression(barSymbol, null);
		
		Expression twoExpression = new RawExpression("2");
		Expression oneExpression = new RawExpression("1");
		
		Statement barDeclaration = new VariableStatement(barSymbol, null);
		
		Statement barSetTwo = new AssignmentStatement(barExpression, twoExpression);
		Statement barSetOne = new AssignmentStatement(barExpression, oneExpression);
		
		container.append(fooDef);
		
		fooDef.append(barSetTwo);
		fooDef.append(barSetOne);
		fooDef.append(barDeclaration);
		
		assertEquals(
			"function foo takes nothing returns nothing\n"
			+ "local integer bar\n"
			+ "set bar=2\n"
			+ "set bar=1\n"
			+ "endfunction",
			container.translate()
		);
	}
	
	@Test
	public void functions() {
		JassContainer container = new JassContainer();
		
		FunctionSymbol fooSymbol = new FunctionSymbol("foo", null, null);
		FunctionSymbol barSymbol = new FunctionSymbol("bar", null, null);
				
		StatementBody fooDef = new FunctionDefinition(fooSymbol);
		StatementBody barDef = new FunctionDefinition(barSymbol);
		
		Expression barExpression = new FunctionExpression(barSymbol, false, new ExpressionList());
		Statement barCall = new FunctionStatement(barExpression);
		
		fooDef.append(barCall);
		
		container.append(fooDef);
		container.append(barDef);
		
		assertEquals(
			"function bar takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "function foo takes nothing returns nothing\n"
			+ "call bar()\n"
			+ "endfunction",
			container.translate()
		);
	}
	
	@Test
	public void mutualRecursion() {
		Type integer = new BuiltInTypeSymbol("integer", null, null);
		Symbol i = new LocalVariableSymbol("i", null, null);
		i.setType(integer);
		
		JassContainer scope = new JassContainer();
		
		FunctionSymbol fooSymbol = new FunctionSymbol("foo", null, null);
		FunctionDefinition fooDef = new FunctionDefinition(fooSymbol);
		
		FunctionSymbol barSymbol = new FunctionSymbol("bar", null, null);
		FunctionDefinition barDef = new FunctionDefinition(barSymbol);
		
		ExpressionList fooArgs = new ExpressionList();
		Expression fooExpression = new FunctionExpression(fooSymbol, false, fooArgs);
		
		ExpressionList barArgs = new ExpressionList();
		Expression barExpression = new FunctionExpression(barSymbol, false, barArgs);
		
		Statement fooReturn = new ReturnStatement(fooExpression);
		Statement barReturn = new ReturnStatement(barExpression);
		
		fooArgs.add(new RawExpression("i"));
		barArgs.add(new RawExpression("i"));
		
		fooSymbol.defineParam(i);
		fooSymbol.setType(integer);
		
		barSymbol.defineParam(i);
		barSymbol.setType(integer);
		
		fooDef.append(barReturn);
		barDef.append(fooReturn);
		
		scope.append(fooDef);
		scope.append(barDef);

		assertEquals(
			"globals\n"
				+ "integer vrjass_c_foo_i\n"
				+ "integer vrjass_c_foo_return\n"
			+ "endglobals\n"
			+ "function vrjass_c_foo takes integer i returns integer\n"
				+ "set vrjass_c_foo_i=i\n"
				+ "call ExecuteFunc(\"vrjass_c_noargs_foo\")\n"
				+ "return vrjass_c_foo_return\n"
			+ "endfunction\n"
			+ "function bar takes integer i returns integer\n"
				+ "return vrjass_c_foo(i)\n"
			+ "endfunction\n"
			+ "function foo takes integer i returns integer\n"
				+ "return bar(i)\n"
			+ "endfunction\n"
			+ "function vrjass_c_foo_noArgs takes nothing returns nothing\n"
			+ "set vrjass_c_foo_return=foo(vrjass_c_foo_i)\n"
			+ "endfunction",
			scope.translate()
		);
	}

}
