package com.ruke.vrjassc.translator;

import com.ruke.vrjassc.Config;
import com.ruke.vrjassc.translator.expression.BooleanExpression;
import com.ruke.vrjassc.translator.expression.BooleanExpression.Operator;
import com.ruke.vrjassc.translator.expression.ElseIfStatement;
import com.ruke.vrjassc.translator.expression.ExpressionList;
import com.ruke.vrjassc.translator.expression.FunctionDefinition;
import com.ruke.vrjassc.translator.expression.FunctionExpression;
import com.ruke.vrjassc.translator.expression.FunctionStatement;
import com.ruke.vrjassc.translator.expression.IfStatement;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.ReturnStatement;
import com.ruke.vrjassc.translator.expression.VariableExpression;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.InterfaceSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class InterfaceMethodDefinition {

	public static FunctionDefinition build(InterfaceSymbol _interface, FunctionSymbol method) {
		FunctionDefinition function = new FunctionDefinition(method);
		IfStatement checks = new IfStatement(new RawExpression("false"));
		ElseIfStatement check = null;
		Symbol implementation = null;
		FunctionExpression func = null;
		ExpressionList params = new ExpressionList();
		
		params.add(new RawExpression("this"));
		
		for (Symbol param : method.getParams()) {
			params.add(new VariableExpression(param, null));
		}
		
		for (ClassSymbol _class : _interface.getImplementations()) {
			check = new ElseIfStatement(
				new BooleanExpression(
					new RawExpression(Config.VTYPE_NAME), 
					Operator.EQUAL_EQUAL, 
					new RawExpression(String.valueOf(_class.getTypeId()))
				)	
			);
			
			implementation = _class.resolve(method.getName());
			func = new FunctionExpression(implementation, false, params);
			
			if (method.getType() == null) {
				check.add(new FunctionStatement(func));
			} else {
				check.add(new ReturnStatement(func));
			}
			
			checks.add(check);
		}
		
		function.add(checks);
		
		if (method.getType() == null) {
			function.add(new FunctionStatement(func));
		} else {
			function.add(new ReturnStatement(func));
		}
		
		return function;
	}
	
}
