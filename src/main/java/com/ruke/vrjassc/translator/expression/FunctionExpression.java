package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.*;
import com.ruke.vrjassc.vrjassc.util.MutualRecursion;
import com.ruke.vrjassc.vrjassc.util.Prefix;
import com.ruke.vrjassc.vrjassc.util.VariableTypeDetector;

import java.util.LinkedList;
import java.util.Stack;

public class FunctionExpression extends Expression {
	
	protected Symbol function;
	protected boolean isCode;
	protected ExpressionList args;
	public boolean useOverrideName;
	
	public FunctionExpression(Symbol function, boolean isCode, ExpressionList args) {
		this.function = function;
		this.isCode = isCode;
		this.args = args;
		this.args.setParent(this);
	}
	
	@Override
	public void setParent(Expression parent) {
		parent.registerFunctionUsage(this.getSymbol());
		super.setParent(parent);
	}
	
	public ExpressionList getArguments() {
		return this.args;
	}

	/*
	 * Since null can be used as object instance we have to translate it to
	 * a default value first
	 */
	private String translateArguments() {
		FunctionSymbol function = (FunctionSymbol) this.getSymbol();
		Stack<Symbol> params = function.getParams();

		if (params.size() == 0) {
			return this.args.translate();
		}

		ExpressionList translatedArgs = new ExpressionList();
		LinkedList<Expression> tal = translatedArgs.getList();

		tal.addAll(this.args.getList());

		for (int i = 0, max = params.size() - 1; i <= max; i++) {
			if (!VariableTypeDetector.isUserType(params.get(i).getType().getName())) {
				continue;
			}

			if (tal.get(i).getSymbol() == null) {
				continue;
			}

			if (!"null".equals(tal.get(i).getSymbol().getType().getName())) {
				continue;
			}

			tal.set(i, new DefaultValue(params.get(i).getType()));
		}

		return translatedArgs.translate();
	}
	
	@Override
	public String translate() {
		FunctionSymbol symbol = (FunctionSymbol) this.getSymbol();
		
		if (symbol.hasModifier(Modifier.OVERRIDE) || !symbol.getImplementations().isEmpty()) {
			if (!this.useOverrideName) {
				symbol = symbol.getOriginal();
			}
		}
		
		String name = Prefix.build(symbol);

		if (this.getParent() instanceof ChainExpression && this.getSymbol().getGeneric() != null) {
			LinkedList<Expression> exprs = ((ChainExpression) this.getParent()).getExpressions();

			for (int i = exprs.size() - 1; i >= 0; i--) {
				if (exprs.get(i).getSymbol().getType() instanceof GenericType) {
					name += "_" + ((Symbol) exprs.get(i).getSymbol().getType()).getGeneric().getName();
					break;
				}
			}
		}
		
		if (this.getParent() != null) {
			MutualRecursion recursion = this.getParent().getMutualRecursion(this.getSymbol());
			
			if (recursion != null) {
				name = recursion.getPrefix();
			}
		}
		
		boolean isInterface = this.getSymbol().getParentScope() instanceof InterfaceSymbol;
		boolean isOverrideable = this.getSymbol().hasModifier(Modifier.OVERRIDE) || !((FunctionSymbol) this.getSymbol()).getImplementations().isEmpty();
		
		if (isInterface || isOverrideable) {
			if (!this.useOverrideName) {
				name += "_vtype";
			}
		}
		
		if (this.isCode) {
			return "function " + name;
		}
		
		return name + "(" + this.translateArguments() + ")";
	}

	@Override
	public Symbol getSymbol() {
		return this.function;
	}

}
