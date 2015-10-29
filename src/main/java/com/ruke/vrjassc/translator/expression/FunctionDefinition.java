package com.ruke.vrjassc.translator.expression;

import java.util.List;

import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;
import com.ruke.vrjassc.vrjassc.util.VariableTypeDetector;

public class FunctionDefinition extends StatementBody {

	private class ParameterList extends ExpressionList {
		private FunctionSymbol function;
		
		public ParameterList(FunctionSymbol function) {
			this.function = function;
		}
		
		@Override
		public String translate() {
			String type;
			
			this.expressions.clear();
			
			for (Symbol param : this.function.getParams()) {
				type = param.getType().getName();
				
				if (VariableTypeDetector.isUserType(type)) {
					type = "integer";
				}
				
				this.add(new RawExpression(type + " " + param.getName()));
			}
			
			if (this.expressions.isEmpty()) {
				return "nothing";
			}
			
			return super.translate();
		}
	}
	
	protected FunctionSymbol function;
	protected ParameterList params;
	protected StatementBody body;
	
	public FunctionDefinition(FunctionSymbol function) {
		this.function = function;
		this.params = new ParameterList(function);
		this.body = new StatementBody();
		
		this.body.setFunctionDefinition(this);
		this.body.setParent(this);
	}
	
	@Override
	public boolean usesFunction(Symbol function) {
		return this.body.usesFunction(function);
	}
	
	@Override
	public void append(Statement statement) {
		statement.setFunctionDefinition(this);
		this.body.append(statement);
	}
	
	@Override
	public String translate() {
		Type _return = this.function.getType();
		String returnType = "nothing";
		
		if (_return != null) {
			returnType = _return.getName();
			
			if (VariableTypeDetector.isUserType(returnType)) {
				returnType = "integer";
			}
		}
		
		return String.format(
			"function %s takes %s returns %s\n"
				+ "%s"
			+ "endfunction",
			this.function.getName(),
			this.params.translate(),
			returnType,
			this.body.translate()
		);
	}
	
	@Override
	public Symbol getSymbol() {
		return this.function;
	}
	
	@Override
	public void sort(List<Statement> list, int index) {
		int newIndex = index;
		int indexFunc;

		for (Statement definition : list) {
			if (definition == this) {
				continue;
			}
			
			if (definition.usesFunction(this.getSymbol())) {
				indexFunc = list.indexOf(definition);
				
				if (indexFunc < newIndex) {
					newIndex = indexFunc;
				} else {
					this.getJassContainer()
						.registerMutualRecursion((FunctionSymbol) function);
				}
			}
		}

		if (newIndex != index) {
			list.remove(index);
			list.add(newIndex, this);
		}
	}
	
}
