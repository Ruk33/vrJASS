package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.Config;
import com.ruke.vrjassc.vrjassc.symbol.*;
import com.ruke.vrjassc.vrjassc.util.Prefix;
import com.ruke.vrjassc.vrjassc.util.VariableTypeDetector;

import java.util.Collection;

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
			
			boolean thisAdded = false;
			
			for (Symbol param : this.function.getParams()) {
				type = param.getType().getName();
				
				if (VariableTypeDetector.isUserType(type)) {
					type = "integer";
				}
				
				this.add(new RawExpression(type + " " + param.getName()));
				
				if (param.getName().equals("this")) thisAdded = true;
			}

			if (this.function.getParentScope() instanceof ClassSymbol) {
				if (!this.function.hasModifier(Modifier.STATIC) && !thisAdded) {
					this.getList().add(0, new RawExpression("integer this"));
				}
			} else if (this.function.getParentScope() instanceof InterfaceSymbol) {
				this.getList().add(0, new RawExpression("integer this"));
				this.getList().add(1, new RawExpression("integer " + Config.VTYPE_NAME));
			}

			if (this.function.getName().equals("allocate")) {
				this.getList().add(0, new RawExpression("integer vrjass_type"));
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
		
		this.body.setParent(this);
		this.params.setParent(this);
	}

	@Override
	public Collection<Symbol> getUsedFunctions() {
		return this.body.getUsedFunctions();
	}
	
	@Override
	public void add(Statement e) {
		if (this.function.getName().equals("allocate")) {
			if (e instanceof ReturnStatement) {
				ChainExpression vtype = new ChainExpression();
				
				vtype.setHashtableName(Config.STRUCT_HASHTABLE_NAME);
				vtype.append(((ReturnStatement) e).getValue(), null);
				vtype.append(new RawExpression(Config.VTYPE_NAME), null);
				
				this.body.add(
					new AssignmentStatement(
						vtype, 
						new RawExpression("vrjass_type")
					)
				);
			}
		}
		
		this.body.add(e);
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
				+ "%s"
			+ "endfunction",
			Prefix.build(this.function),
			this.params.translate(),
			returnType,
			this.body.getDeclarations().translate(),
			this.body.translate()
		);
	}
	
	@Override
	public Symbol getSymbol() {
		return this.function;
	}

	public boolean hasMutualRecursionWith(Statement def) {
		return this.getUsedFunctions().contains(def.getSymbol()) &&
				def.getUsedFunctions().contains(this.getSymbol());
	}
}
