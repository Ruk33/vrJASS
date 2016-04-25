package com.ruke.vrjassc.translator;

import com.ruke.vrjassc.Config;
import com.ruke.vrjassc.translator.expression.*;
import com.ruke.vrjassc.translator.expression.BooleanExpression.Operator;
import com.ruke.vrjassc.vrjassc.symbol.*;
import com.ruke.vrjassc.vrjassc.util.Prefix;

import java.util.LinkedList;

public class SymbolOverrideTranslator {

	private static BooleanExpression getConditionCheck(Symbol symbol) {
		if (symbol instanceof UserTypeSymbol == false) {
			return getConditionCheck((Symbol) symbol.getParentScope());
		}
		
		return new BooleanExpression(
			new RawExpression(Config.VTYPE_NAME), 
			Operator.EQUAL_EQUAL, 
			new RawExpression(String.valueOf(((UserTypeSymbol) symbol).getTypeId()))
		);
	}
	
	private static Statement getCheckBody(Symbol implementation, ExpressionList params) {
		FunctionExpression func = new FunctionExpression(implementation, false, params);
		func.useOverrideName = true;
		
		Statement result = null;
		
		if (implementation.getType() == null) {
			result = new FunctionStatement(func);
		} else {
			result = new ReturnStatement(func);
		}
		
		return result;
	}
	
	public static FunctionDefinition build(Overrideable original, FunctionSymbol method) {
		FunctionSymbol e = new FunctionSymbol(Prefix.build(method) + "_vtype", null, null);
		
		Symbol integer = method.resolve("integer");
		Symbol _this = new Symbol("this", null, null);
		Symbol vtype = new Symbol("vtype", null, null);
		
		_this.setType((Type) integer);
		vtype.setType((Type) integer);
		
		if (!method.hasModifier(Modifier.STATIC)) {
			e.defineParam(_this);
		}
		
		e.defineParam(vtype);
		e.defineParam(method.getParams());
		e.setType(method.getType());
		
		FunctionDefinition function = new FunctionDefinition(e);
		LinkedList<Symbol> implementations = new LinkedList<Symbol>(original.getImplementations());
		ExpressionList params = new ExpressionList();
		
		IfStatement checks = null;
		StatementBody check = null;
		
		Symbol first = null;
		Symbol last = null;
		
		if (original instanceof FunctionSymbol && !((FunctionSymbol) original).hasModifier(Modifier.ABSTRACT)) {
			implementations.add((Symbol) original);
		}
		
		if (implementations.size() == 0) {
			return function;
		}
		
		first = implementations.removeFirst();
		
		if (implementations.size() == 0) {
			last = first;
		} else {
			last = implementations.removeLast();
		}
		
		if (!method.hasModifier(Modifier.STATIC)) {
			params.add(new RawExpression("this"));
		}

		if (method.getName().equals("allocate")) {
			params.add(new RawExpression("vtype"));
		}
		
		for (Symbol param : method.getParams()) {
			params.add(new VariableExpression(param, null));
		}
		
		checks = new IfStatement(getConditionCheck(first));
		checks.add(getCheckBody(((ScopeSymbol) first).resolve(method.getName()), params));
		
		for (Symbol implementation : implementations) {
			check = new ElseIfStatement(getConditionCheck(implementation));
			check.add(getCheckBody(((ScopeSymbol) implementation).resolve(method.getName()), params));
			checks.add(check);
		}
		
		check = new ElseStatement();
		check.add(getCheckBody(((ScopeSymbol) last).resolve(method.getName()), params));
		
		checks.add(check);
		function.add(checks);
		
		return function;
	}
	
}
