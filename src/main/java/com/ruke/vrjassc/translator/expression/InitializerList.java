package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.InitializerHandler;
import com.ruke.vrjassc.vrjassc.util.Prefix;

public class InitializerList extends Statement {

	protected InitializerHandler initializerHandler;

	public InitializerList(InitializerHandler initializerHandler) {
		this.initializerHandler = initializerHandler;
	}
	
	@Override
	public String translate() {
		StatementList result = new StatementList();
		Expression executeFunc;
		String name;
		
		for (Symbol initializer : this.initializerHandler.getInitializers()) {
			name = Prefix.build(initializer);
			executeFunc = new RawExpression("ExecuteFunc(\""+ name +"\")");
			result.add(new FunctionStatement(executeFunc));
		}
		
		return result.translate();
	}
	
}
