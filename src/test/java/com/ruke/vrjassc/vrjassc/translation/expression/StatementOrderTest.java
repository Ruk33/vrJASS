package com.ruke.vrjassc.vrjassc.translation.expression;

import com.ruke.vrjassc.translator.expression.*;
import com.ruke.vrjassc.vrjassc.symbol.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
		
		container.add(fooDef);
		
		fooDef.add(barSetTwo);
		fooDef.add(barSetOne);
		fooDef.add(barDeclaration);
		
		assertEquals(
			"globals\n"
			+ "endglobals\n"
			+ "function foo takes nothing returns nothing\n"
				+ "local integer bar=0\n"
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
		
		fooDef.add(barCall);
		
		container.add(fooDef);
		container.add(barDef);
		
		assertEquals(
			"globals\n"
			+ "endglobals\n"
			+ "function bar takes nothing returns nothing\n"
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
		
		fooSymbol.defineParam(i);
		fooSymbol.setType(integer);
		
		barSymbol.defineParam(i);
		barSymbol.setType(integer);
		
		ExpressionList fooArgs = new ExpressionList();
		Expression fooExpression = new FunctionExpression(fooSymbol, false, fooArgs);
		
		ExpressionList barArgs = new ExpressionList();
		Expression barExpression = new FunctionExpression(barSymbol, false, barArgs);
		
		Statement fooReturn = new ReturnStatement(fooExpression);
		Statement barReturn = new ReturnStatement(barExpression);
		
		fooArgs.add(new RawExpression("i"));
		barArgs.add(new RawExpression("i"));
		
		fooDef.add(barReturn);
		barDef.add(fooReturn);
		
		scope.add(fooDef);
		scope.add(barDef);

		assertEquals(
			"globals\n"
				+ "integer vrjass_c_bar_i=0\n"
				+ "integer vrjass_c_bar_return=0\n"
			+ "endglobals\n"
			+ "function vrjass_c_bar takes integer i returns integer\n"
				+ "set vrjass_c_bar_i=i\n"
				+ "call ExecuteFunc(\"vrjass_c_bar_noArgs\")\n"
				+ "return vrjass_c_bar_return\n"
			+ "endfunction\n"
			+ "function foo takes integer i returns integer\n"
				+ "return vrjass_c_bar(i)\n"
			+ "endfunction\n"
			+ "function bar takes integer i returns integer\n"
				+ "return foo(i)\n"
			+ "endfunction\n"
			+ "function vrjass_c_bar_noArgs takes nothing returns nothing\n"
				+ "set vrjass_c_bar_return=bar(vrjass_c_bar_i)\n"
			+ "endfunction",
			scope.translate()
		);
	}

}
