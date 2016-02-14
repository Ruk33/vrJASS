package com.ruke.vrjassc.translator;

import java.util.LinkedList;

import com.ruke.vrjassc.Config;
import com.ruke.vrjassc.translator.expression.BooleanExpression;
import com.ruke.vrjassc.translator.expression.BooleanExpression.Operator;
import com.ruke.vrjassc.translator.expression.ElseIfStatement;
import com.ruke.vrjassc.translator.expression.ElseStatement;
import com.ruke.vrjassc.translator.expression.ExpressionList;
import com.ruke.vrjassc.translator.expression.FunctionDefinition;
import com.ruke.vrjassc.translator.expression.FunctionExpression;
import com.ruke.vrjassc.translator.expression.FunctionStatement;
import com.ruke.vrjassc.translator.expression.IfStatement;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.ReturnStatement;
import com.ruke.vrjassc.translator.expression.Statement;
import com.ruke.vrjassc.translator.expression.StatementBody;
import com.ruke.vrjassc.translator.expression.VariableExpression;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.InterfaceSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class InterfaceMethodDefinition {

	private static BooleanExpression getConditionCheck(ClassSymbol _class) {
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
		LinkedList<ClassSymbol> implementations = new LinkedList<ClassSymbol>(_interface.getImplementations());
		ClassSymbol first = null;
		ClassSymbol last = null;
		
		if (implementations.size() == 0) {
			return function;
		}
		
		first = implementations.removeFirst();
		
		if (implementations.size() == 0) {
			last = first;
		} else {
			last = implementations.removeLast();
		}
		
		params.add(new RawExpression("this"));
		
		for (Symbol param : method.getParams()) {
			params.add(new VariableExpression(param, null));
		}
		
		checks = new IfStatement(getConditionCheck(first));
		checks.add(getCheckBody(first.resolve(method.getName()), params));
		
		for (ClassSymbol _class : _interface.getImplementations()) {
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
