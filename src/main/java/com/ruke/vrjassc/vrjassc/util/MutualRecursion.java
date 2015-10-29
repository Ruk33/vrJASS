package com.ruke.vrjassc.vrjassc.util;

import java.util.Stack;

import com.ruke.vrjassc.translator.expression.FunctionDefinition;
import com.ruke.vrjassc.translator.expression.FunctionStatement;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.Statement;
import com.ruke.vrjassc.translator.expression.StatementBody;
import com.ruke.vrjassc.translator.expression.VariableStatement;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class MutualRecursion {

	protected FunctionSymbol function;
	
	protected FunctionSymbol dummy;
	protected FunctionSymbol dummyNoArgs;
	
	public MutualRecursion(FunctionSymbol function) {
		this.function = function;
		this.dummyNoArgs = new FunctionSymbol(this.getDummyNoArgsName(), null, null);
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
		StatementBody globalsBlock = new StatementBody();
		Stack<Symbol> variables = new Stack<Symbol>();
		Symbol _return;
		Symbol temp;
		
		variables.addAll(this.function.getParams());
		
		if (this.function.getType() != null) {
			_return = new Symbol("return", null, null);
			_return.setType(this.function.getType());
			
			variables.push(_return);
		}
		
		for (Symbol variable : variables) {
			temp = new Symbol(this.getGlobalVariableName(variable), null, null);
			temp.setType(variable.getType());
			
			globalsBlock.append(new VariableStatement(temp, null));
		}
		
		return globalsBlock;
	}
	
	public Statement getDummyDefinition() {
		this.dummy = new FunctionSymbol(this.getPrefix(), null, null);
		
		StatementBody functionDefinition = new FunctionDefinition(this.dummy);
		
		functionDefinition.append(
			new FunctionStatement(
				new RawExpression(
					"ExecuteFunc(\"" + this.getDummyNoArgsName() + "\")"
				)
			)
		);
		
		for (Symbol param : this.function.getParams()) {
			this.dummy.defineParam(param);
		}
		
		this.dummy.setType(this.function.getType());
		
		return functionDefinition;
	}
	
	public Statement getDummyNoArgsDefinition() {
		return new FunctionDefinition(this.dummyNoArgs);
	}
	
}
