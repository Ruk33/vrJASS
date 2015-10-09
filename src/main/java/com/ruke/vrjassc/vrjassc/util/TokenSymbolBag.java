package com.ruke.vrjassc.vrjassc.util;

import java.util.Hashtable;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionOrVariableContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LocalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MethodDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParameterContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PropertyStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.SetVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StructDefinitionContext;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

/**
 * Avoid classes having to know how to store and get symbols from tokens
 * @author Ruke
 *
 */
public class TokenSymbolBag {

	private Hashtable<Token, Symbol> bag = new Hashtable<Token, Symbol>();
	
	public void saveVariable(LocalVariableStatementContext ctx, Symbol variable) {
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
	
	public void saveFromChainExpression(FunctionOrVariableContext ctx, Symbol s) {
		this.bag.put(ctx.getStart(), s);
	}
	
	public Symbol getFromChainExpression(FunctionOrVariableContext ctx) {
		return this.bag.get(ctx.getStart());
	}
	
}
