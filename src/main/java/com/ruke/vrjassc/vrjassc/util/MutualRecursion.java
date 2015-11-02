package com.ruke.vrjassc.vrjassc.util;

import java.util.Stack;

import com.ruke.vrjassc.translator.expression.AssignmentStatement;
import com.ruke.vrjassc.translator.expression.Expression;
import com.ruke.vrjassc.translator.expression.ExpressionList;
import com.ruke.vrjassc.translator.expression.FunctionDefinition;
import com.ruke.vrjassc.translator.expression.FunctionExpression;
import com.ruke.vrjassc.translator.expression.FunctionStatement;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.ReturnStatement;
import com.ruke.vrjassc.translator.expression.Statement;
import com.ruke.vrjassc.translator.expression.StatementList;
import com.ruke.vrjassc.translator.expression.VariableExpression;
import com.ruke.vrjassc.translator.expression.VariableStatement;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class MutualRecursion {

	protected FunctionSymbol function;
	
	protected StatementList globals;
	protected Stack<Symbol> globalArgs;
	
	protected Expression returnExpression;
	protected Statement returnStatement;
	
	protected FunctionDefinition dummyDefinition;
	protected FunctionDefinition dummyNoArgsDefinition;
	
	protected void defineGlobals() {
		Symbol variable;
		
		this.globals = new StatementList();
		this.globalArgs = new Stack<Symbol>();
		
		for (Symbol param : this.function.getParams()) {
			variable = new Symbol(this.getGlobalVariableName(param), null, null);
			variable.setType(param.getType());
			
			this.globals.add(new VariableStatement(variable, null));
			this.globalArgs.push(variable);
		}
		
		if (this.function.getType() != null) {
			variable = new Symbol(this.getPrefix() + "_return", null, null);
			variable.setType(this.function.getType());
			
			this.returnExpression = new VariableExpression(variable, null);
			this.globals.add(new VariableStatement(variable, null));
		}
		
		this.returnStatement = new ReturnStatement(this.returnExpression);
	}
	
	protected void defineDummyFunction() {
		FunctionSymbol function = new FunctionSymbol(this.getPrefix(), null, null);
		
		function.setType(this.function.getType());
		function.defineParam(this.function.getParams());
		
		this.dummyDefinition = new FunctionDefinition(function);
		
		String paramName;
		
		for (Symbol arg : this.globalArgs) {
			paramName = arg.getName().replaceFirst(this.getPrefix() + "_", "");
			
			this.dummyDefinition.add(
				new AssignmentStatement(
					new VariableExpression(arg, null),
					new RawExpression(paramName)
				)
			);
		}
		
		String noArgsName = this.getDummyNoArgsName();
		Expression execFuncExpr = new RawExpression("ExecuteFunc(\"" + noArgsName + "\")");
		Statement noArgsCall = new FunctionStatement(execFuncExpr);
		
		this.dummyDefinition.add(noArgsCall);
		this.dummyDefinition.add(this.returnStatement);
	}
	
	protected void defineDummyNoArgsFunction() {
		FunctionSymbol function = new FunctionSymbol(this.getDummyNoArgsName(), null, null);
		
		this.dummyNoArgsDefinition = new FunctionDefinition(function);
		
		ExpressionList args = new ExpressionList();
		Expression functionCall = new FunctionExpression(this.function, false, args);
		Statement statement;
		
		for (Symbol arg : this.globalArgs) {
			args.add(new VariableExpression(arg, null));
		}
		
		if (this.function.getType() == null) {
			statement = new FunctionStatement(functionCall);
		} else {
			statement = new AssignmentStatement(this.returnExpression, functionCall);
		}
		
		this.dummyNoArgsDefinition.add(statement);
	}
	
	public MutualRecursion(FunctionSymbol function) {
		this.function = function;
		
		this.defineGlobals();
		this.defineDummyFunction();
		this.defineDummyNoArgsFunction();
	}
	
	public String getPrefix() {
		return "vrjass_c_" + this.function.getName();
	}
	
	public String getGlobalVariableName(Symbol variable) {
		return this.getPrefix() + "_" + variable.getName();
	}
	
	public String getDummyNoArgsName() {
		return this.getPrefix() + "_noArgs";
	}
	
	public Statement getGlobalVariableBlock() {
		return this.globals;
	}
	
	public Statement getDummyDefinition() {
		return this.dummyDefinition;
	}
	
	public Statement getDummyNoArgsDefinition() {
		return this.dummyNoArgsDefinition;
	}
	
}
