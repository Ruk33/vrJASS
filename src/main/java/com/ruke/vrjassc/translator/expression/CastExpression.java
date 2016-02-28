package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.CastSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class CastExpression extends Expression {

	protected Expression original;
	
	public CastExpression(Expression original, Expression cast) {		
		this.original = new RawExpression(
			original.translate(), 
			new CastSymbol(original.getSymbol(), cast.getSymbol(), null)
		);
		
		this.original.setParent(this);
	}
	
	@Override
	public Symbol getSymbol() {
		return this.original.getSymbol();
	}
	
	@Override
	public String translate() {
		return this.original.translate();
	}

}
