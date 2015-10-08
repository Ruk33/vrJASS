package com.ruke.vrjassc.vrjassc.phase;

import java.util.Collection;
import java.util.Stack;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ChainExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionOrVariableContext;
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

public class TranslationPhase extends vrjassBaseVisitor<Void> {

	protected TokenSymbolBag symbols;
	protected ChainExpressionTranslator chainTranslator;
	
	protected StringBuilder globals;
	protected StringBuilder output;
	
	protected int classEnum;
	protected int propertyEnum;
	protected boolean settingProperty;
	
	public TranslationPhase(TokenSymbolBag symbols, Scope scope) {
		this.output = new StringBuilder();
		this.globals = new StringBuilder();
		
		this.symbols = symbols;
		this.chainTranslator = new ChainExpressionTranslator();
		
		this.chainTranslator.setHashtableName(this.getClassesHashtableName());
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
	
	protected String getTranslatedPropertyGetter(Collection<FunctionOrVariableContext> ctx) {
		Stack<Symbol> symbols = new Stack<Symbol>();
		
		for (FunctionOrVariableContext expr : ctx) {
			symbols.add(this.symbols.getFromChainExpression(expr));
		}
		
		return this.chainTranslator.getGetter(symbols);
	}
	
	protected String getTranslatedPropertySetter(Collection<FunctionOrVariableContext> ctx, String value) {
		String result = this.getTranslatedPropertyGetter(ctx);
		
		result.replace("Load", "Save");
		result.substring(0, result.length()-1);
		result.concat(value);
		result.concat(")");
		
		return result;
	}
	
	protected String getTranslatedProperty(Stack<FunctionOrVariableContext> ctx, boolean setter) {
		if (setter) {
			return this.getTranslatedPropertySetter(ctx, "epa!");
		}
		
		return this.getTranslatedPropertyGetter(ctx);
	}
	
	protected String getTranslatedProperty(ChainExpressionContext ctx, boolean setter) {
		Stack<FunctionOrVariableContext> members = new Stack<FunctionOrVariableContext>();
		members.addAll(ctx.functionOrVariable());
		
		return this.getTranslatedProperty(members, setter);
	}
	
	@Override
	public Void visitChainExpression(ChainExpressionContext ctx) {
		this.output.append(this.getTranslatedProperty(ctx, this.settingProperty));
		return null;
	}
	
	@Override
	public Void visitReturnStatement(ReturnStatementContext ctx) {
		this.output.append("return ");
		this.visit(ctx.expression());
		this.output.append("\n");
		
		return null;
	}
	
	@Override
	public Void visitSetVariableStatement(SetVariableStatementContext ctx) {
		if (ctx.name.getClass().getSimpleName().equals("MemberContext")) {
			this.settingProperty = true;
			
			this.output.append("call ");
			super.visitSetVariableStatement(ctx);
			
			this.settingProperty = false;
		} else {
			this.output.append("set...");
		}
		
		this.output.append("\n");
		
		return null;
	}
	
	@Override
	public Void visitStructDefinition(StructDefinitionContext ctx) {
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
	public Void visitPropertyStatement(PropertyStatementContext ctx) {
		this.propertyEnum++;
		
		Symbol property = this.symbols.getProperty(ctx);
		
		this.globals.append(String.format(
			"integer %s=%d\n",
			this.getTranslatedPropertyName(property),
			this.propertyEnum
		));
		
		return null;
	}
	
	@Override
	public Void visitParameter(ParameterContext ctx) {
		Symbol param = this.symbols.getParameter(ctx);
		String type = param.getType().getName();
		
		if (VariableTypeDetector.isUserType(type)) {
			type = "integer";
		}
		
		this.output.append(type + " " + param.getName());
		
		return null;
	}
	
	@Override
	public Void visitReturnType(ReturnTypeContext ctx) {
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
	public Void visitMethodDefinition(MethodDefinitionContext ctx) {
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
