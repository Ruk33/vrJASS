package com.ruke.vrjassc.vrjassc.phase;

import java.util.Hashtable;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ChainExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionOrVariableContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.IntegerContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MethodDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParameterContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PropertyStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ReturnStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ReturnTypeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.SetVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StructDefinitionContext;
import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.Scope;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.ChainExpressionTranslator;
import com.ruke.vrjassc.vrjassc.util.TokenSymbolBag;
import com.ruke.vrjassc.vrjassc.util.VariableTypeDetector;

public class TranslationPhase extends vrjassBaseVisitor<String> {

	protected TokenSymbolBag symbols;
	
	protected StringBuilder globals;
	protected StringBuilder output;
	
	protected int classEnum;
	protected int propertyEnum;
	protected String propertyValue;
	
	protected Hashtable<Symbol, String> propertiesKey = new Hashtable<Symbol, String>();
	
	public TranslationPhase(TokenSymbolBag symbols, Scope scope) {
		this.output = new StringBuilder();
		this.globals = new StringBuilder();
		
		this.symbols = symbols;
	}
	
	public String getClassesHashtableName() {
		return "vr_structs";
	}
	
	public String getTranslatedClassName(Symbol _class) {
		return "struct_" + _class.getName() + "_vr_type";
	}
	
	public String getTranslatedMethodName(Symbol method) {
		return "struct_" + method.getParentScope().getName() + "_" + method.getName();
	}
	
	public String getTranslatedPropertyName(Symbol property) {
		return "struct_" + property.getParentScope().getName() + "_" + property.getName();
	}
	
	@Override
	public String visitInteger(IntegerContext ctx) {
		return ctx.getText();
	}
	
	@Override
	public String visitChainExpression(ChainExpressionContext ctx) {
		ChainExpressionTranslator ct = new ChainExpressionTranslator();
		String value = this.propertyValue;
		
		Symbol symbol;
		String index;
		
		this.propertyValue = null;
		
		for (FunctionOrVariableContext expr : ctx.functionOrVariable()) {
			symbol = this.symbols.getFromChainExpression(expr);
			index = null;
			
			if (expr.variableExpression() != null) {
				if (expr.variableExpression().index != null) {
					index = this.visit(expr.variableExpression().index);
				}
			}
			
			ct.append(symbol, index, this.propertiesKey.get(symbol));
		}
		
		this.propertyValue = value;
		return ct.build(value);
	}
	
	@Override
	public String visitReturnStatement(ReturnStatementContext ctx) {
		this.output.append("return ");
		
		if (ctx.expression() != null) {
			this.output.append(this.visit(ctx.expression()));
		}
		
		this.output.append("\n");
		
		return null;
	}
	
	@Override
	public String visitSetVariableStatement(SetVariableStatementContext ctx) {
		if (ctx.name.getClass().getSimpleName().equals("MemberContext")) {
			this.propertyValue = this.visit(ctx.value);
			this.output.append("call " + this.visit(ctx.name));
			this.propertyValue = null;
		} else {
			this.output.append("set...");
		}
		
		this.output.append("\n");
		
		return null;
	}
	
	@Override
	public String visitStructDefinition(StructDefinitionContext ctx) {
		this.classEnum++;
		
		if (this.classEnum == 1) {
			this.globals.append(String.format(
				"hashtable %s=InitHashtable()\n",
				this.getClassesHashtableName()
			));
		}
		
		Symbol _class = this.symbols.getClass(ctx);
		
		this.globals.append(String.format(
			"integer %s=%d\n",
			this.getTranslatedClassName(_class),
			this.classEnum
		));
		
		return super.visitStructDefinition(ctx);
	}
	
	@Override
	public String visitPropertyStatement(PropertyStatementContext ctx) {
		Symbol property = this.symbols.getProperty(ctx);
		String key = this.getTranslatedPropertyName(property);
		
		this.propertyEnum++;
		
		this.globals.append(String.format(
			"integer %s=%d\n",
			this.getTranslatedPropertyName(property),
			this.propertyEnum
		));
		
		this.propertiesKey.put(property, key);
		
		return null;
	}
	
	@Override
	public String visitParameter(ParameterContext ctx) {
		Symbol param = this.symbols.getParameter(ctx);
		String type = param.getType().getName();
		
		if (VariableTypeDetector.isUserType(type)) {
			type = "integer";
		}
		
		this.output.append(type + " " + param.getName());
		
		return null;
	}
	
	@Override
	public String visitReturnType(ReturnTypeContext ctx) {
		String returnType = "nothing";
		
		if (ctx.NOTHING() == null) {
			returnType = ctx.validType().getText();
			
			if (VariableTypeDetector.isUserType(returnType)) {
				returnType = "integer";
			}
		}
		
		this.output.append(returnType);
		
		return null;
	}
		
	@Override
	public String visitMethodDefinition(MethodDefinitionContext ctx) {
		Symbol method = this.symbols.getMethod(ctx);
		
		this.output.append("function ");
		this.output.append(this.getTranslatedMethodName(method));
		
		this.output.append(" takes ");
		if (ctx.parameters().NOTHING() != null) {
			if (method.hasModifier(Modifier.STATIC)) {
				this.output.append("nothing");
			} else {
				this.output.append("integer this");
			}
		} else {
			this.visit(ctx.parameters());
		}
		
		this.output.append(" returns ");
		this.visit(ctx.returnType());
		this.output.append("\n");
		
		this.visit(ctx.statements());
		
		this.output.append("endfunction");
		
		return null;
	}

	public String getOutput() {
		return
				"globals\n" +
					this.globals.toString() +
				"endglobals\n" +
				this.output.toString();
	}
	
}
