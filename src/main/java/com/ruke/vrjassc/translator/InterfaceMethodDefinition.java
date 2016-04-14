package com.ruke.vrjassc.translator;

import com.ruke.vrjassc.Config;
import com.ruke.vrjassc.translator.expression.*;
import com.ruke.vrjassc.translator.expression.BooleanExpression.Operator;
import com.ruke.vrjassc.vrjassc.symbol.*;

import java.util.LinkedList;

public class InterfaceMethodDefinition {

	private static BooleanExpression getConditionCheck(UserTypeSymbol _class) {
		return new BooleanExpression(
			new RawExpression(Config.VTYPE_NAME), 
			Operator.EQUAL_EQUAL, 
			new RawExpression(String.valueOf(_class.getTypeId()))
		);
	}
	
	private static Statement getCheckBody(Symbol implementation, ExpressionList params) {
		FunctionExpression func = new FunctionExpression(implementation, false, params);
		Statement result = null;
		
		if (implementation.getType() == null) {
			result = new FunctionStatement(func);
		} else {
			result = new ReturnStatement(func);
		}
		
		return result;
	}
	
	public static FunctionDefinition build(InterfaceSymbol _interface, FunctionSymbol method) {
		FunctionDefinition function = new FunctionDefinition(method);
		IfStatement checks = null;
		StatementBody check = null;
		ExpressionList params = new ExpressionList();
		LinkedList<Symbol> implementations = new LinkedList<Symbol>(_interface.getImplementations());
		ClassSymbol first = null;
		ClassSymbol last = null;
		
		if (implementations.size() == 0) {
			return function;
		}
		
		first = (ClassSymbol) implementations.removeFirst();
		
		if (implementations.size() == 0) {
			last = first;
		} else {
			last = (ClassSymbol) implementations.removeLast();
		}
		
		params.add(new RawExpression("this"));
		
		for (Symbol param : method.getParams()) {
			params.add(new VariableExpression(param, null));
		}
		
		checks = new IfStatement(getConditionCheck(first));
		checks.add(getCheckBody(first.resolve(method.getName()), params));

		ClassSymbol _class;

		for (Symbol implementation : _interface.getImplementations()) {
			_class = (ClassSymbol)implementation;
			check = new ElseIfStatement(getConditionCheck(_class));
			check.add(getCheckBody(_class.resolve(method.getName()), params));
		}
		
		check = new ElseStatement();
		check.add(getCheckBody(last.resolve(method.getName()), params));
		
		checks.add(check);
		function.add(checks);
		
		return function;
	}
	
}
