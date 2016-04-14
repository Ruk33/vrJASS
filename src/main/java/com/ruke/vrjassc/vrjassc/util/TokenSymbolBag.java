package com.ruke.vrjassc.vrjassc.util;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.Hashtable;

/**
 * Avoid classes having to know how to store and get symbols from tokens
 * @author Ruke
 *
 */
public class TokenSymbolBag {

	private Hashtable<Token, Symbol> bag = new Hashtable<Token, Symbol>();
	
	public void put(ParserRuleContext ctx, Symbol symbol) {
		this.bag.putIfAbsent(ctx.getStart(), symbol);
	}
	
	public Symbol get(ParserRuleContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
	/*
	public void saveVariable(LocalVariableStatementContext ctx, Symbol variable) {
		this.bag.put(ctx.getStart(), variable);
	}
	
	public Symbol getVariable(ThisContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
	public void saveVariable(ThisContext ctx, Symbol variable) {
		this.bag.put(ctx.getStart(), variable);
	}
	
	public Symbol getVariable(LocalVariableStatementContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
	public void saveVariable(SetVariableStatementContext ctx, Symbol variable) {
		this.bag.put(ctx.getStart(), variable);
	}
	
	public Symbol getVariable(SetVariableStatementContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
	public Symbol getVariable(VariableExpressionContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
	public void saveVariable(VariableExpressionContext ctx, Symbol variable) {
		this.bag.put(ctx.getStart(), variable);
	}
	
	public void saveParameter(ParameterContext ctx, Symbol variable) {
		this.bag.put(ctx.getStart(), variable);
	}
	
	public Symbol getParameter(ParameterContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
	public void saveFunction(FunctionDefinitionContext ctx, Symbol function) {
		this.bag.put(ctx.getStart(), function);
	}
	
	public Symbol getFunction(FunctionDefinitionContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
	public void saveLibrary(LibraryDefinitionContext ctx, Symbol library) {
		this.bag.put(ctx.getStart(), library);
	}
	
	public Symbol getLibrary(LibraryDefinitionContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
	public void saveGlobalVariable(GlobalVariableStatementContext ctx, Symbol variable) {
		this.bag.put(ctx.getStart(), variable);
	}
	
	public Symbol getGlobalVariable(GlobalVariableStatementContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
	public void saveClass(StructDefinitionContext ctx, Symbol _class) {
		this.bag.put(ctx.getStart(), _class);
	}
	
	public Symbol getClass(StructDefinitionContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
	public void saveMethod(MethodDefinitionContext ctx, Symbol method) {
		this.bag.put(ctx.getStart(), method);
	}
	
	public Symbol getMethod(MethodDefinitionContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
	public void saveProperty(PropertyStatementContext ctx, Symbol property) {
		this.bag.put(ctx.getStart(), property);
	}
	
	public Symbol getProperty(PropertyStatementContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
	public void saveFromChainExpression(ExpressionContext ctx, Symbol s) {
		this.bag.put(ctx.getStart(), s);
	}
	
	public Symbol getFromChainExpression(ExpressionContext ctx) {
		return this.bag.get(ctx.getStart());
	}

	public Symbol getFunction(FunctionExpressionContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
	public void saveFunction(FunctionExpressionContext ctx, Symbol function) {
		this.bag.put(ctx.getStart(), function);
	}

	public void saveVariable(ThisExpressionContext ctx, Symbol _this) {
		this.bag.put(ctx.getStart(), _this);
	}
	
	public Symbol getVariable(ThisExpressionContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	*/
}
